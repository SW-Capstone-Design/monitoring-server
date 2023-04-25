package kr.co.monitoringserver.service.service.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.DuplicatedException;
import kr.co.monitoringserver.infra.global.exception.InvalidInputException;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceStatusService {

    private final AttendanceStatusRepository attendanceStatusRepository;

    private final UserRepository userRepository;

    private final AttendanceStatusMapper attendanceStatusMapper;

    /**
     * Attendance Status Service
     * Get : getTardinessList, getAbsentList 지각자, 결석자 목록 조회
        * 지각, 결석으로 처리된 모든 사용자의 목록을 조회하는 기능
     * Get : 출석률 조회
         * 특정 기간 동안의 출석률을 조회하는 기능
         * 시작 날짜와 종료 날짜를 파라미터로 받아 해당 기간 동안의 출석률을 계산하여 반환
     */

    /**
     * Create Attendance Status Service
     */

    @Transactional
    public void createAttendanceStatus(AttendStatusReqDTO.CREATE create) {

        isAttendanceAvailable(create.getUserId());

        final User user = userRepository.findById(create.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        final AttendanceType goWorkType = Optional.ofNullable(create.getEnterTime())
                .map(this::calculateGoWorkAttendanceType)
                .orElseThrow(InvalidInputException::new);

        final AttendanceType leaveWorkType = Optional.ofNullable(create.getLeaveTime())
                .map(this::calculateLeaveWorkAttendanceType)
                .orElseThrow(InvalidInputException::new);

        AttendanceStatus attendanceStatus =
                attendanceStatusMapper.toAttendStatusEntity(create, user, goWorkType, leaveWorkType);

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

        Map<AttendanceType, Integer> attendanceDays =
                calculateAttendanceDays(Collections.singletonList(attendanceStatus));

        return attendanceStatusMapper.toAttendStatusReadDto(attendanceStatus, attendanceDays, user);
    }

    /**
     * Get Tardiness User Attendance Status By Date Service
     */
    public List<AttendStatusResDTO.READ> getTardinessUserByDate(LocalDate date) {

        final List<AttendanceStatus> attendanceStatuses = attendanceStatusRepository.findByDate(date);

        return attendanceStatuses.stream()
                .filter(this::isLate)
                .map(attendanceStatusMapper::toAttendTypeReadDto)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Get Absent User Attendance Status By userId Service
     */
    public List<AttendStatusResDTO.READ> getAbsentUserByDate(LocalDate date) {

        final List<AttendanceStatus> attendanceStatuses = attendanceStatusRepository.findByDate(date);

        return attendanceStatuses
                .stream()
                .filter(this::isAbsent)
                .map(attendanceStatusMapper::toAttendTypeReadDto)
                .distinct()
                .collect(Collectors.toList());
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

        AttendanceType goWorkType = Optional.ofNullable(update.getEnterTime())
                .map(this::calculateGoWorkAttendanceType)
                .orElse(null);

        AttendanceType leaveWorkType = Optional.ofNullable(update.getLeaveTime())
                .map(this::calculateLeaveWorkAttendanceType)
                .orElse(null);

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
    private AttendanceType calculateGoWorkAttendanceType(LocalTime enterTime) {

        LocalTime startTime = LocalTime.parse("08:00:00");

        if (enterTime == null) {
            return AttendanceType.ABSENT;
        } else if (enterTime.isBefore(startTime)) {
            return AttendanceType.GO_WORK;
        } else {
            return AttendanceType.TARDINESS;
        }
    }

    private AttendanceType calculateLeaveWorkAttendanceType(LocalTime leaveTime) {

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

        for (AttendanceType type : AttendanceType.values()) {
            attendanceDays.put(type, 0);
        }

        for (AttendanceStatus attendanceStatus : attendanceStatuses) {
            AttendanceType goWorkType = attendanceStatus.getGoWork();
            if (goWorkType != null) {
                attendanceDays.put(goWorkType, attendanceDays.get(goWorkType) + 1);
            }
        }

        for (AttendanceStatus attendanceStatus : attendanceStatuses) {
            AttendanceType leaveWorkType = attendanceStatus.getLeaveWork();
            if (leaveWorkType != null) {
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

    private boolean isLate(AttendanceStatus attendanceStatus) {

        return attendanceStatus.getGoWork() == AttendanceType.TARDINESS;
    }

    private boolean isAbsent(AttendanceStatus attendanceStatus) {

        return attendanceStatus.getGoWork() == AttendanceType.ABSENT
                || attendanceStatus.getLeaveWork() == AttendanceType.ABSENT;
    }
}
