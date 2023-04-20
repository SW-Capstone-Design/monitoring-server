package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus, Long> {

    List<AttendanceStatus> findByAttendance(Attendance attendance);
}
