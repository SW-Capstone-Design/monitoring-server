package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.securityArea.Position;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.securityArea.UserSecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.SecurityAreaRepository;
import kr.co.monitoringserver.persistence.repository.UserSecurityAreaRepository;
import kr.co.monitoringserver.service.dtos.response.UserSecurityAreaResDTO;
import kr.co.monitoringserver.service.enums.RoleType;
import kr.co.monitoringserver.service.mappers.UserSecurityAreaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSecurityAreaService {

    private final UserSecurityAreaRepository userSecurityAreaRepository;

    private final SecurityAreaRepository securityAreaRepository;

    private final WarningNotificationService warningNotificationService;

    private final UserSecurityAreaMapper userSecurityAreaMapper;


    /**
     * Create Security Access Log Service
     */
    @Transactional
    public void createSecurityAccessLog(User user, SecurityArea securityArea) {

        UserSecurityArea userSecurityArea =
                userSecurityAreaMapper.toUserSecurityAreaEntity(user, securityArea, LocalTime.now());

        userSecurityAreaRepository.save(userSecurityArea);

        if (!user.getRoleType().equals(RoleType.ADMIN)) {
            warningNotificationService.createAndSendWarningNotification(user);
        }
    }

    /**
     * Get Security Access Log By User And Security Area Service
     */
    public Page<UserSecurityAreaResDTO.READ> getSecurityAccessLogByArea(User user, SecurityArea securityArea, Pageable pageable) {

        final Page<UserSecurityArea> userSecurityAreaPage =
                userSecurityAreaRepository.findByUserAndSecurityArea(user, securityArea, pageable);

        return userSecurityAreaPage.map(userSecurityAreaMapper::toUserSecurityAreaReadDto);
    }

    /**
     * listSecurityAccessLog : 모든 SecurityArea 접근 기록을 조회한다.
     */
    public Page<UserSecurityArea> listSecurityAccessLog(Pageable pageable) {

        return userSecurityAreaRepository.findAll(pageable);
    }

    // 보안구역 접근 권한 검사
    public SecurityArea verifyAccessToSecurityArea(String securityAreaName) {

        SecurityArea securityArea = securityAreaRepository.findByName(securityAreaName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SECURITY_AREA));

        return securityArea;
    }

    // 하버사인 공식
    public double haversineDistance(Position userLocation, Position securityAreaLocation) {

        final double R = 6371e3; // 반경(meters)

        double lat1 = Math.toRadians(userLocation.getLatitude());
        double lon1 = Math.toRadians(userLocation.getLongitude());

        double lat2 = Math.toRadians(securityAreaLocation.getLatitude());
        double lon2 = Math.toRadians(securityAreaLocation.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    // 사용자 위치와 보안구역 위치를 비교
    public boolean isWithinRange(Position userLocation, Position securityAreaLocation, double range) {

        double distance = haversineDistance(userLocation, securityAreaLocation);

        return distance <= range;
    }
}
