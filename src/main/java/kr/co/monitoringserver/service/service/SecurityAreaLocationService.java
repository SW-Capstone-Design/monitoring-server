package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.securityArea.UserSecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.UserSecurityAreaRepository;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaLocationResDTO;
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

    private final SecurityAreaMapper securityAreaMapper;

    private final WarningNotificationService warningNotificationService;

    /**
     * Is User Inside SecurityArea Service
     */
    public boolean isUserInsideSecurityArea(User user, SecurityArea securityArea) {

        // 사용자의 x, y 좌표를 가져온다
        double x = user.getLocation().getX();
        double y = user.getLocation().getY();

        // 보안구역의 x1, y1, x2, y2 좌표를 가져온다
        double x1 = securityArea.getLowerLeft().getX();
        double y1 = securityArea.getLowerLeft().getY();

        double x2 = securityArea.getUpperRight().getX();
        double y2 = securityArea.getUpperRight().getY();

        // 사용자가 보안구역 내에 있는지 확인한다
        boolean isInsideSecurityArea = (x1 <= x && x <= x2) && (y1 <= y && y <= y2);

        // 해당 결과를 반환한다
        return isInsideSecurityArea;
    }

    /**
     * Create SecurityArea Access Log Service
     */
    public void createSecurityAreaAccessLog(boolean isInsideSecurityArea, boolean isAuthorization, User user, SecurityArea securityArea) {

        // 사용자가 보안구역 내에 있을 경우(조건이 참일 경우), 보안구역 출입 기록 정보 생성
        if (isInsideSecurityArea && isAuthorization) {
            UserSecurityArea userSecurityArea = securityAreaMapper.toUserSecurityAreaEntity(user, securityArea, LocalTime.now());

            userSecurityAreaRepository.save(userSecurityArea);
        } else {
            warningNotificationService.createAndSendWarningNotification(user);
        }
    }

    /**
     * Get Security Access Log By User And Security Area Service
     */
    public Page<SecurityAreaLocationResDTO.READ> getSecurityAccessLogByArea(User user, SecurityArea securityArea, Pageable pageable) {

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
}
