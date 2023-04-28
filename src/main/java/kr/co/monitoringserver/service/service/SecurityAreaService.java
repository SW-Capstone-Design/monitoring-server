package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.repository.SecurityAreaRepository;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaResDTO;
import kr.co.monitoringserver.service.mappers.SecurityAreaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityAreaService {

    private final SecurityAreaRepository securityAreaRepository;

    private final SecurityAreaMapper securityAreaMapper;

    /**
     * Create Security Area Service
     */
    @Transactional
    public void createSecurityArea(SecurityAreaReqDTO.CREATE create) {

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
     * Update Security Area Service
     */
    @Transactional
    public void updateSecurityArea(Long securityAreaId, SecurityAreaReqDTO.UPDATE update) {

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SECURITY_AREA));

        securityArea.updateSecurityArea(update);
    }
}
