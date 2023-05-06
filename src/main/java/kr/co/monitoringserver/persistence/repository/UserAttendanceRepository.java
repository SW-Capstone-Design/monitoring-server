package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserAttendanceRepository extends JpaRepository<UserAttendance, Long> {

    Page<UserAttendance> findByAttendance_Date(LocalDate date, Pageable pageable);

    Page<UserAttendance> findByUser_Identity(String identity, Pageable pageable);

    Optional<UserAttendance> findByUserAndAttendance_Date(User user, LocalDate date);
}
