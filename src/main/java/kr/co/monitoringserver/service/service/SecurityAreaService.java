package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.repository.SecurityAreaRepository;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
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


}
