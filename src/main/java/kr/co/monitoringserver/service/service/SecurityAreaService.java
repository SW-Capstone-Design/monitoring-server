package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.BadRequestException;
import kr.co.monitoringserver.infra.global.exception.NotAuthenticateException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.securityArea.Position;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.SecurityAreaRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaResDTO;
import kr.co.monitoringserver.service.enums.RoleType;
import kr.co.monitoringserver.service.mappers.SecurityAreaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityAreaService {

    private final SecurityAreaRepository securityAreaRepository;

    private final SecurityAreaMapper securityAreaMapper;

    private final UserRepository userRepository;

    private final SecurityAccessLogService securityAccessLogService;

    /**
     * Create Security Area Service
     */
    @Transactional
    public void createSecurityArea(String userIdentity, SecurityAreaReqDTO.CREATE create) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        verifyAccessToSecurityArea(user.getRoleType());

        SecurityArea securityArea = securityAreaMapper.toSecurityAreaEntity(create);

        securityAreaRepository.save(securityArea);
    }

    /**
     * Detecting Access To Security Area Service
     */
    @Transactional
    public void detectingAccessToSecurityArea(String userIdentity, Long securityAreaId) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SECURITY_AREA));

        isWithinRange(user.getUserLocation(), securityArea.getSecurityAreaLocation(), 20);

        securityAccessLogService.createSecurityAccessLog(user, securityArea);
    }

    /**
     * Get Security Area By id Service
     */
    public Page<SecurityAreaResDTO.READ> getSecurityAreaById(Long securityAreaId, Pageable pageable) {

        Page<SecurityArea> securityAreaPage = securityAreaRepository.findById(securityAreaId, pageable);

        return securityAreaPage.map(securityAreaMapper::toSecurityAreaReadDto);
    }


    /**
     * securityAreaDetail : SecurityAreaId로 조회하여 엔티티 타입 객체로 반환한다.
     */
    public SecurityArea securityAreaDetail(Long securityAreaId){

        return securityAreaRepository.findById(securityAreaId)
                .orElseThrow(()->{
                    return new IllegalArgumentException("보안구역 조회 실패 : 해당 보안구역 아이디를 찾을 수 없습니다.");
                });
    }

    /**
     * getSecurityArea : SecurityArea 의 모든 정보를 Select 하여 Page 를 반환한다.
     */
    public Page<SecurityArea> getSecurityArea(Pageable pageable) {

        return securityAreaRepository.findAll(pageable);
    }


    /**
     * Update Security Area Service
     */
    @Transactional
    public void updateSecurityArea(Long securityAreaId, SecurityAreaReqDTO.UPDATE update) {

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SECURITY_AREA));

        securityArea.updateSecurityArea(update);
    }

    /**
     * Delete Security Area Service
     */
    @Transactional
    public void deleteSecurityArea(Long securityAreaId) {

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SECURITY_AREA));

        securityAreaRepository.delete(securityArea);
    }



    // 보안구역 접근 권한 검사
    private void verifyAccessToSecurityArea(RoleType roleType) {

        if (!roleType.equals(RoleType.ADMIN)) {
            throw new NotAuthenticateException();
        }
    }

    // 하버사인 공식
    private double haversineDistance(Position userLocation, Position securityAreaLocation) {

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
    private boolean isWithinRange(Position userLocation, Position securityAreaLocation, double range) {

        double distance = haversineDistance(userLocation, securityAreaLocation);

        return distance <= range;
    }

}
