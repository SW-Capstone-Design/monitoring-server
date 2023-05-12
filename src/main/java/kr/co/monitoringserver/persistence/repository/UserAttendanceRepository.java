package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.service.enums.AttendanceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface UserAttendanceRepository extends JpaRepository<UserAttendance, Long> {

    Page<UserAttendance> findByUser_UserId(Long userId, Pageable pageable);

    Page<UserAttendance> findByUser_Identity(String identity, Pageable pageable);

    Optional<UserAttendance> findByUserAndAttendance_Date(User user, LocalDate date);

    Page<UserAttendance> findByAttendance_Date(LocalDate date, Pageable pageable);

    UserAttendance findByUser_UserIdAndAttendance_Date(Long userId, LocalDate date);

    Page<UserAttendance> findByAttendance_GoWorkAndAttendance_Date(AttendanceType goWorkType, LocalDate date, Pageable pageable);

    Page<UserAttendance> findByAttendance_LeaveWorkAndAttendance_Date(AttendanceType goWorkType, LocalDate date, Pageable pageable);

    Long countByUser_UserIdAndAttendance_GoWork(Long userId, AttendanceType goWorkType);

    Long countByUser_UserIdAndAttendance_LeaveWork(Long userId, AttendanceType leaveWorkType);

    Long countByUser_UserIdAndAttendance_GoWorkAndAttendance_LeaveWork(Long userId, AttendanceType goWorkType, AttendanceType leaveWorkType);
}
