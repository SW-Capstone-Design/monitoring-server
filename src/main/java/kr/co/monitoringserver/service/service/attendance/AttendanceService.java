package kr.co.monitoringserver.service.service.attendance;

import com.sun.jdi.request.DuplicateRequestException;
import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.DuplicatedException;
import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.mappers.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    /**
     * 출석 서비스
     * 출석 기록과 출석 상태 간의 관계를 설정하고, 이를 활용하여 출석 상태를 출력하는 기능
     */

    private final UserRepository userRepository;

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    /** Create Attendance Service
     *
     */
    @Transactional
    public void createAttendance(AttendanceReqDTO.CREATE create, Long userId) {

        LocalDate attendanceDate = LocalDate.now();

        Attendance existAttendance = attendanceRepository.findByUserIdAndAttendanceData(userId, attendanceDate);
        if (existAttendance != null) {
            throw new DuplicatedException(ErrorCode.DUPLICATE_ATTENDANCE);
        }

        LocalTime enterTime = create.getEnterTime();
        LocalTime leaveTime = create.getLeaveTime();

        AttendanceType attendanceType = calculateAttendanceStatus(enterTime, leaveTime);

        Attendance attendance = attendanceMapper.toAttendacneEntity(create, attendanceType);

        attendanceRepository.save(attendance);
    }

    /** Get Attendance By attendanceId Service
     *
     */
    public AttendanceResDTO.READ getAttendanceById(Long attendanceId) {

        final Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Not Found Attendance"));

        return attendanceMapper.toAttendacneReadDto(attendance);
    }



    private AttendanceType calculateAttendanceStatus(LocalTime enterTime,
                                                     LocalTime leaveTime) {

        LocalTime startTime = LocalTime.parse("09:00:00");
        LocalTime endTime = LocalTime.parse("18:00:00");

        if (enterTime.isBefore(startTime.plusMinutes(10))) {
            return AttendanceType.GO_WORK;
        } else if (enterTime.isBefore(startTime.plusMinutes(30))) {
            return AttendanceType.TARDINESS;
        } else if (leaveTime.isBefore(endTime.minusMinutes(30))) {
            return AttendanceType.EARLY_LEAVE;
        } else {
            return AttendanceType.LEAVE_WORK;
        }
    }
}
