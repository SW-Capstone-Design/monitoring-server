package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.entity.securityArea.SecurityAccessLog;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.SecurityAccessLogRepository;
import kr.co.monitoringserver.service.enums.RoleType;
import kr.co.monitoringserver.service.mappers.SecurityAccessLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class SecurityAccessLogService {

    private final SecurityAccessLogRepository securityAccessLogRepository;

    private final WarningNotificationService warningNotificationService;

    private final SecurityAccessLogMapper securityAccessLogMapper;


    /**
     * Create Security Access Log
     */
    @Transactional
    public void createSecurityAccessLog(User user, SecurityArea securityArea) {

        SecurityAccessLog securityAccessLog =
                securityAccessLogMapper.toSecurityAccessLogEntity(user, securityArea, LocalTime.now());

        securityAccessLogRepository.save(securityAccessLog);

        if (!user.getRoleType().equals(RoleType.ADMIN)) {
            warningNotificationService.createAndSendWarningNotification(user);
        }
    }
}
