package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.BadRequestException;
import kr.co.monitoringserver.infra.global.exception.NotAuthenticateException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
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

    /** TODO : 비인가 사용자에 대한 권한 확인 및 경고 알림 생성
     * 비인가 사용자가 해당 보안 구역을 들어갈 경우 사용자의 권한을 확인
     * 보안 구역 정보를 생성할 경우 상황에 맞는 경고 알림을 조회하고 없을 경우 경고 알림 정보를 생성
     * 비인가 사용자가 해당 보안 구역에 들어갈 경우 경고 알림이 울림
     */

    /**
     * Create Security Area Service
     */
    @Transactional
    public void createSecurityArea(String userIdentity, SecurityAreaReqDTO.CREATE create) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        checkAuthenticate(user.getRoleType());

        SecurityArea securityArea = securityAreaMapper.toSecurityAreaEntity(create);

        securityAreaRepository.save(securityArea);
    }

    /**
     * Get Security Area By id Service
     */
    public SecurityAreaResDTO.READ getSecurityAreaById(Long securityAreaId) {

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SECURITY_AREA));

        return securityAreaMapper.toSecurityAreaReadDto(securityArea);
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
     * getSecurityArea : SecurityArea의 모든 정보를 Select하여 Page를 반환한다.
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


    private void checkAuthenticate(RoleType roleType) {

        if (!roleType.equals(RoleType.ADMIN)) {
            throw new NotAuthenticateException();
        }
    }
}
