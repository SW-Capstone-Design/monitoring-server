package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.securityArea.UserSecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.SecurityAreaRepository;
import kr.co.monitoringserver.persistence.repository.UserSecurityAreaRepository;
import kr.co.monitoringserver.service.dtos.response.UserSecurityAreaResDTO;
import kr.co.monitoringserver.service.enums.RoleType;
import kr.co.monitoringserver.service.mappers.SecurityAreaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityAreaLocationService {

    private final UserSecurityAreaRepository userSecurityAreaRepository;

    private final SecurityAreaRepository securityAreaRepository;

    private final WarningNotificationService warningNotificationService;

    private final SecurityAreaMapper securityAreaMapper;


    // 4. 사용자와 보안 구역의 위치를 비교하는 로직
    public boolean isUserInsideZone(Location userLocation, SecurityArea securityArea) {

        // 사용자 위치와 보안 구역의 위치를 비교하여 접근 여부를 판별합니다.

    }

    // 사용자가 보안 구역 내에 접근하는 경우 보안 구역 출입 기록 정보를 생성하는 메서드
    public void saveUserSecurityZoneAccessRecord(SecurityArea securityArea, boolean isUserAuthorized){
        // 사용자 ID, 보안 구역 ID, 접근 시간, 인가 상태등의 정보를 사용자-보안 구역 테이블에 저장합니다.
    }


    /**
     * Create Security Access Log Service
     */
    @Transactional
    public void createUserSecurityAreaAccessLog(User user, SecurityArea securityArea) {

        UserSecurityArea userSecurityArea = securityAreaMapper.toUserSecurityAreaEntity(user, securityArea, LocalTime.now());

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

        return userSecurityAreaPage.map(securityAreaMapper::toUserSecurityAreaReadDto);
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
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_SECURITY_AREA));

        return securityArea;
    }
}
