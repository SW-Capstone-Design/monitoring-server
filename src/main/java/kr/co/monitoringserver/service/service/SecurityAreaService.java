package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.exception.BadRequestException;
import kr.co.monitoringserver.infra.global.exception.NotAuthenticateException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.SecurityAreaRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaResDTO;
import kr.co.monitoringserver.service.dtos.response.UserSecurityAreaResDTO;
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

    private final UserSecurityAreaService userSecurityAreaService;

    /**
     * Create Security Area Service
     */
    @Transactional
    public void createSecurityArea(String userIdentity, SecurityAreaReqDTO.CREATE create) {

        checkSecurityAreaAccess(userIdentity);

        SecurityArea securityArea = securityAreaMapper.toSecurityAreaEntity(create);

        securityAreaRepository.save(securityArea);
    }

    /**
     * Get Security Area By id Service
     */
    public Page<SecurityAreaResDTO.READ> getSecurityAreaById(String userIdentity, Long securityAreaId, Pageable pageable) {

        checkSecurityAreaAccess(userIdentity);

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
    public void updateSecurityArea(String userIdentity, Long securityAreaId, SecurityAreaReqDTO.UPDATE update) {

        checkSecurityAreaAccess(userIdentity);

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_SECURITY_AREA));

        securityArea.updateSecurityArea(update);
    }

    /**
     * Delete Security Area Service
     */
    @Transactional
    public void deleteSecurityArea(String userIdentity, Long securityAreaId) {

        checkSecurityAreaAccess(userIdentity);

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_SECURITY_AREA));

        securityAreaRepository.delete(securityArea);
    }



    /**
     * Detecting Access To User Security Area Service
     */
    @Transactional
    public void detectingAccessToUserSecurityArea(String userIdentity, String securityAreaName) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        final SecurityArea securityArea = userSecurityAreaService.verifyAccessToSecurityArea(securityAreaName);

//        if (userSecurityAreaService.isWithinRange(user.getUserLocation(), securityArea.getSecurityAreaLocation(), 20)) {
//        }

        userSecurityAreaService.createSecurityAccessLog(user, securityArea);
    }

    /**
     * Get User Security Area By User And Security Area Service
     */
    public Page<UserSecurityAreaResDTO.READ> getUserSecurityAreaByUserAndSecurityArea(String userIdentity, String securityAreaName, Pageable pageable) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        final SecurityArea securityArea = userSecurityAreaService.verifyAccessToSecurityArea(securityAreaName);

        return userSecurityAreaService.getSecurityAccessLogByArea(user, securityArea, pageable);
    }



    // 사용자 권한 검사
    private void checkSecurityAreaAccess(String userIdentity) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        if (!user.getRoleType().equals(RoleType.ADMIN)) {
            throw new NotAuthenticateException();
        }
    }
}
