package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByUser(User user);

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findAllByAttendanceDateBetween(LocalDate startDate, LocalDate endDate);
}
