package kr.co.monitoringserver.service.service.alert;

import kr.co.monitoringserver.persistence.entity.alert.WarningNotification;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.WarningNotificationRepository;
import kr.co.monitoringserver.service.mappers.WarningNotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class WarningNotificationService {

    private final WarningNotificationRepository warningNotificationRepository;

    private final WarningNotificationMapper warningNotificationMapper;

    /**
     * Create And Send Warning Notification
     */
    public void createAndSendWarningNotification(User user) {

        WarningNotification warningNotification =
                warningNotificationMapper.toWarningNotificationEntity(
                        LocalTime.now(),
                        "주의 : " + user.getName() + "님이 보안구역에 진입하였습니다");

        warningNotificationRepository.save(warningNotification);

        sendNotification(user, warningNotification);
    }

    // 경고알림 출력
    private void sendNotification(User user, WarningNotification warningNotification) {

        System.out.println("알림 전송: " + user.getName() + ", 메시지: " + warningNotification.getContent());
    }
}
