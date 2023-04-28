package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.UserAttendance;
import kr.co.monitoringserver.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAttendanceRepository extends JpaRepository<UserAttendance, Long> {

    List<UserAttendance> findByAttendance_Date(LocalDate date);

    List<UserAttendance> findByUser_UserId(Long userId);

    Optional<UserAttendance> findByUser(User user);
}
