package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.attendance.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Page<Attendance> findByDate(LocalDate searchKeyword, Pageable pageable);
}
