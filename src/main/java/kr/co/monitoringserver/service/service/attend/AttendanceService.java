package kr.co.monitoringserver.service.service.attend;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.User;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceStatus;
import kr.co.monitoringserver.service.mappers.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.Temporal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final UserRepository userRepository;

    private final AttendanceMapper attendanceMapper;

    @Transactional
    public void createAttendance(AttendanceReqDTO.CREATE create) {

        Attendance attendance = attendanceMapper.toAttendacneEntity(create);

        attendanceRepository.save(attendance);
    }

    public AttendanceResDTO.READ getAttendanceById(Long attendanceId) {

        final Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Not Found Attendance"));

        return attendanceMapper.toAttendacneReadDto(attendance);
    }

    public void updateAttendance(AttendanceReqDTO.UPDATE update) {

        final User user = userRepository.findById(update.getUserId())
                .orElseThrow(() -> new RuntimeException("Not Found User"));


    }


    private AttendanceStatus calculateAttendanceStatus(LocalDate inTime,
                                                       LocalDate outTime) {

        LocalDate startTime = LocalDate.parse("09:00:00");
        LocalDate endTime = LocalDate.parse("18:00:00");

        LocalDate currentTime = LocalDate.now();

        if (currentTime.isBefore((ChronoLocalDate) startTime.plus(Duration.ofMinutes(10))) {
            // 출근 처리
        } else if (currentTime.isBefore((ChronoLocalDate) startTime.plus(Duration.ofMinutes(30))) {
            // 지각 처리
        } else if (currentTime.isBefore(endTime.minusMinutes(30))) {
            // 조퇴 처리
        } else {
            // 퇴근 처리
        }
    }
}
