package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus, Long> {

    Optional<AttendanceStatus> findByUser(User user);

    Optional<AttendanceStatus> findByUserAndDate(Long userId, LocalDate date);
}
