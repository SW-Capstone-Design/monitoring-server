package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.alert.IndexNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndexNotificationRepository extends JpaRepository<IndexNotification, Long> {
}
