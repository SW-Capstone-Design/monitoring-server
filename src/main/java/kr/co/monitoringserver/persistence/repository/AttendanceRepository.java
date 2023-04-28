package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
