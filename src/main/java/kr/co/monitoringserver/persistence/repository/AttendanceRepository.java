package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query(" select a " +
            "from AttendanceStatus a " +
            "where a.user = :user")
    List<AttendanceStatus> findByUser(@Param("user") User user);

    @Query(" select a " +
            "from AttendanceStatus a " +
            "where a.date = :date")
    List<AttendanceStatus> findByAttendanceStatusDate(@Param("date") LocalDate date);

    @Query(" select a " +
            "from AttendanceStatus a " +
            "where a.date between :startDate and :endDate")
    List<AttendanceStatus> findAllByAttendanceStatusBetween(LocalDate startDate, LocalDate endDate);
}
