package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAttendanceRepository extends JpaRepository<UserAttendance, Long> {

    List<UserAttendance> findByAttendance_Date(LocalDate date);

    List<UserAttendance> findListByUser_UserId(Long userId);

    Page<UserAttendance> findByUser_UserId(Long userId, Pageable pageable);

    Optional<UserAttendance> findByUser(User user);

    Page<UserAttendance> findByAttendance_Date(LocalDate searchKeyword, Pageable pageable);
}
