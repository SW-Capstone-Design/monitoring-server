package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.alert.WarningNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarningNotificationRepository extends JpaRepository<WarningNotification, Long> {
}
