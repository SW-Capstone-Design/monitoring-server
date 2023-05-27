package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.alert.IndexNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IndexNotificationRepository extends JpaRepository<IndexNotification, Long> {

    Optional<IndexNotification> findByIndexAlertId(Long indexAlertId);

    List<IndexNotification> findTop10ByOrderByIndexAlertTimeDesc();

    Long countBy();

    IndexNotification findByIndexAlertContent(String indexAlertContent);
}
