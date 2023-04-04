package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository<Attendance, Long> {
}
