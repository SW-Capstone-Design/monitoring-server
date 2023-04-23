package kr.co.monitoringserver.service.service.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.DuplicatedException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.persistence.entity.User;
import kr.co.monitoringserver.persistence.repository.AttendanceStatusRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendStatusResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.mappers.AttendanceStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceStatusService {

    private final AttendanceStatusRepository attendanceStatusRepository;

    private final UserRepository userRepository;

    private final AttendanceStatusMapper attendanceStatusMapper;

    /**
     * Create Attendance Status Service
     * 출석 가능 여부 검사 & 해당 날짜 출석 중복 여부 검사
     */
    @Transactional
    public void createAttendanceStatus(AttendStatusReqDTO.CREATE create) {

        isAttendanceAvailable(create.getUserId());

        final User user = userRepository.findById(create.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        AttendanceType goWorkType = updateAttendanceTypeGoWork(create.getEnterTime());
        AttendanceType leaveWorkType = updateAttendanceTypeLeaveWork(create.getLeaveTime());

        AttendanceStatus attendanceStatus = attendanceStatusMapper.toAttendStatusEntity(create, user, goWorkType, leaveWorkType);

        attendanceStatusRepository.save(attendanceStatus);
    }

    /**
     * Get Attendance Status By userId Service
     */
    public AttendStatusResDTO.READ getAttendanceStatusById(Long userId) {

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        final AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE_STATUS));

        Map<AttendanceType, Integer> attendanceDays = calculateAttendanceDays(Collections.singletonList(attendanceStatus));

        return attendanceStatusMapper.toAttendStatusReadDto(attendanceStatus, attendanceDays, user);
    }

    /**
     * Update Attendance Status Service
     */
    @Transactional
    public void updateAttendanceStatus(AttendStatusReqDTO.UPDATE update) {

        final User user = userRepository.findById(update.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        final AttendanceStatus attendanceStatus = attendanceStatusRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE_STATUS));

        AttendanceType goWorkType = updateAttendanceTypeGoWork(update.getEnterTime());
        AttendanceType leaveWorkType = updateAttendanceTypeLeaveWork(update.getLeaveTime());

        attendanceStatus.updateAttendanceStatus(update, goWorkType, leaveWorkType);
    }

    /**
     * Delete Attendance Status By id Service
     */
    @Transactional
    public void deleteAttendanceStatus(Long attendanceStatusId) {

        final AttendanceStatus attendanceStatus = attendanceStatusRepository.findById(attendanceStatusId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE_STATUS));

        attendanceStatusRepository.delete(attendanceStatus);
    }


    /**
     * 출석 상태 판별 기능
     * 출근 : 08:00:00 이전 출근할 경우
     * 지각 : 08:00:00 이후 or 17:00:00 이전 출근할 경우
     * 조퇴 : 정상 출근 후 17:00:00 이전 퇴근할 경우
     * 결근 : 08:00:00 ~ 17:00:00 사이 어떠한 출/퇴근도 없을 경우
     * 퇴근 : 17:00:00 이후 퇴근할 경우
     */
    private AttendanceType updateAttendanceTypeGoWork(LocalTime enterTime) {

        LocalTime startTime = LocalTime.parse("08:00:00");

        if (enterTime == null) {
            return AttendanceType.ABSENT;
        } else if (enterTime.isBefore(startTime)) {
            return AttendanceType.GO_WORK;
        } else {
            return AttendanceType.TARDINESS;
        }
    }

    private AttendanceType updateAttendanceTypeLeaveWork(LocalTime leaveTime) {

        LocalTime endTime = LocalTime.parse("17:00:00");

        if (leaveTime == null) {
            return AttendanceType.ABSENT;
        } else if (leaveTime.isAfter(endTime)) {
            return AttendanceType.LEAVE_WORK;
        } else {
            return AttendanceType.EARLY_LEAVE;
        }
    }

    private Map<AttendanceType, Integer> calculateAttendanceDays(List<AttendanceStatus> attendanceStatuses) {

        // TODO : 정상 출/퇴근이 이루어질 경우 ATTENDANCE 의 값을 증가

        Map<AttendanceType, Integer> attendanceDays = new HashMap<>();

        attendanceDays.put(AttendanceType.GO_WORK, 0);
        attendanceDays.put(AttendanceType.LEAVE_WORK, 0);
        attendanceDays.put(AttendanceType.ABSENT, 0);
        attendanceDays.put(AttendanceType.TARDINESS, 0);
        attendanceDays.put(AttendanceType.EARLY_LEAVE, 0);

        for (AttendanceStatus attendanceStatus : attendanceStatuses) {
            AttendanceType goWorkType = attendanceStatus.getGoWork();

            if (goWorkType != null) {
                goWorkType.getCount();
                attendanceDays.put(goWorkType, attendanceDays.get(goWorkType) + 1);
            }
        }

        for (AttendanceStatus attendanceStatus : attendanceStatuses) {

            AttendanceType leaveWorkType = attendanceStatus.getLeaveWork();
            if (leaveWorkType != null) {
                leaveWorkType.getCount();
                attendanceDays.put(leaveWorkType, attendanceDays.get(leaveWorkType) + 1);
            }
        }

        return attendanceDays;
    }

    private boolean isAttendanceAvailable(Long userId) {

       LocalDate today = LocalDate.now();

       Optional<AttendanceStatus> attendanceStatusOpt = attendanceStatusRepository.findByUserAndDate(userId, today);

        if (attendanceStatusOpt.isPresent()) {
            AttendanceStatus attendanceStatus = attendanceStatusOpt.get();
            if (attendanceStatus.getDate().equals(today)) {
                throw new DuplicatedException(ErrorCode.DUPLICATE_USER_ATTENDANCE);
            }
            return false;
        }
        return true;
    }
}
