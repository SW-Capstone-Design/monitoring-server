package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.securityArea.SecurityAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityAccessLogRepository extends JpaRepository<SecurityAccessLog, Long> {
}
