package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus, Long> {
}
