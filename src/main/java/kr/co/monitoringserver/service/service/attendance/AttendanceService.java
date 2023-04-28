//package kr.co.monitoringserver.service.service.attendance;
//
//import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
//import kr.co.monitoringserver.infra.global.exception.InvalidInputException;
//import kr.co.monitoringserver.infra.global.exception.NotFoundException;
//import kr.co.monitoringserver.persistence.entity.Attendance;
//import kr.co.monitoringserver.persistence.entity.UserAttendance;
//import kr.co.monitoringserver.persistence.entity.User;
//import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
//import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
//import kr.co.monitoringserver.persistence.repository.UserRepository;
//import kr.co.monitoringserver.service.dtos.request.UserAttendanceReqDTO;
//import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
//import kr.co.monitoringserver.service.enums.AttendanceType;
//import kr.co.monitoringserver.service.mappers.AttendanceMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class AttendanceService {
//
//    private final AttendanceRepository attendanceRepository;
//
//    private final UserAttendanceRepository userAttendanceRepository;
//
//    private final UserRepository userRepository;
//
//    private final AttendanceMapper attendanceMapper;
//
//    /**
//     * Create UserAttendance Status Service
//     */
//    @Transactional
//    public void createAttendanceStatus(Long userId, UserAttendanceReqDTO.CREATE create) {
//
//        final User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
//
//        List<UserAttendance> userAttendances = userAttendanceRepository.findByUser(user);
//
//        final AttendanceType goWork = Optional.ofNullable(create.getEnterTime())
//                .map(this::calculateGoWorkAttendanceType)
//                .orElseThrow(InvalidInputException::new);
//
//        final AttendanceType leaveWork = Optional.ofNullable(create.getLeaveTime())
//                .map(this::calculateLeaveWorkAttendanceType)
//                .orElseThrow(InvalidInputException::new);
//
//        Attendance attendance =
//                attendanceMapper.toAttendanceStatusEntity(create, goWork, leaveWork, userAttendances);
//
//        attendanceRepository.save(attendance);
//    }
//
//    /**
//     * Get UserAttendance Status By userId Service
//     */
//    public AttendanceResDTO.READ getAttendanceStatusByUserId(Long userId) {
//
//        final User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
//
//        List<UserAttendance> userAttendances = userAttendanceRepository.findByUser(user);
//
//        List<Attendance> attendances = attendanceRepository.findByAttendanceUsersIn(userAttendances);
//
//        Map<AttendanceType, Integer> attendanceDays = calculateAttendanceDays(attendances);
//
//        return attendanceMapper.toAttendStatusReadDto(attendances, attendanceDays, userAttendances);
//    }
//
//    /**
//     * Get Tardiness User UserAttendance Status By Date Service
//     */
//    public List<AttendanceResDTO.READ> getTardinessUserByDate(LocalDate date) {
//
//        final List<Attendance> attendances = attendanceRepository.findByDate(date);
//
//        return attendances.stream()
//                .filter(this::isLate)
//                .map(attendanceMapper::toAttendTypeReadDto)
//                .distinct()
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Get Absent User UserAttendance Status By userId Service
//     */
//    public List<AttendanceResDTO.READ> getAbsentUserByDate(LocalDate date) {
//
//        final List<Attendance> attendances = attendanceRepository.findByDate(date);
//
//        return attendances
//                .stream()
//                .filter(this::isAbsent)
//                .map(attendanceMapper::toAttendTypeReadDto)
//                .distinct()
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Update UserAttendance Status Service
//     */
//    @Transactional
//    public void updateAttendanceStatus(Long userId, UserAttendanceReqDTO.UPDATE update) {
//
//        final User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
//
//        List<UserAttendance> userAttendances = userAttendanceRepository.findByUser(user);
//
//        List<Attendance> attendances = attendanceRepository.findByAttendanceUsersIn(userAttendances);
//
//        AttendanceType goWorkType = Optional.ofNullable(update.getEnterTime())
//                .map(this::calculateGoWorkAttendanceType)
//                .orElse(null);
//
//        AttendanceType leaveWorkType = Optional.ofNullable(update.getLeaveTime())
//                .map(this::calculateLeaveWorkAttendanceType)
//                .orElse(null);
//
//        for (Attendance attendance : attendances) {
//            attendance.updateAttendanceStatus(update, goWorkType, leaveWorkType);
//        }
//
//        attendanceRepository.saveAll(attendances);
//    }
//
//    /**
//     * Delete UserAttendance Status By id Service
//     */
//    @Transactional
//    public void deleteAttendanceStatus(Long attendanceStatusId) {
//
//        final Attendance attendance = attendanceRepository.findById(attendanceStatusId)
//                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE_STATUS));
//
//        attendanceRepository.delete(attendance);
//    }
//
//
//    /**
//     * 출석 상태 판별 기능
//     * 출근 : 08:00:00 이전 출근할 경우
//     * 지각 : 08:00:00 이후 or 17:00:00 이전 출근할 경우
//     * 조퇴 : 정상 출근 후 17:00:00 이전 퇴근할 경우
//     * 결근 : 08:00:00 ~ 17:00:00 사이 어떠한 출/퇴근도 없을 경우
//     * 퇴근 : 17:00:00 이후 퇴근할 경우
//     */
//    private AttendanceType calculateGoWorkAttendanceType(LocalTime enterTime) {
//
//        LocalTime startTime = LocalTime.parse("08:00:00");
//
//        if (enterTime == null) {
//            return AttendanceType.ABSENT;
//        } else if (enterTime.isBefore(startTime)) {
//            return AttendanceType.GO_WORK;
//        } else {
//            return AttendanceType.TARDINESS;
//        }
//    }
//
//    private AttendanceType calculateLeaveWorkAttendanceType(LocalTime leaveTime) {
//
//        LocalTime endTime = LocalTime.parse("17:00:00");
//
//        if (leaveTime == null) {
//            return AttendanceType.ABSENT;
//        } else if (leaveTime.isAfter(endTime)) {
//            return AttendanceType.LEAVE_WORK;
//        } else {
//            return AttendanceType.EARLY_LEAVE;
//        }
//    }
//
//    private Map<AttendanceType, Integer> calculateAttendanceDays(List<Attendance> attendances) {
//
//        // TODO : 정상 출/퇴근이 이루어질 경우 ATTENDANCE 의 값을 증가
//
//        Map<AttendanceType, Integer> attendanceDays = new HashMap<>();
//
//        for (AttendanceType type : AttendanceType.values()) {
//            attendanceDays.put(type, 0);
//        }
//
//        for (Attendance attendance : attendances) {
//            AttendanceType goWorkType = attendance.getGoWork();
//            if (goWorkType != null) {
//                attendanceDays.put(goWorkType, attendanceDays.get(goWorkType) + 1);
//            }
//        }
//
//        for (Attendance attendance : attendances) {
//            AttendanceType leaveWorkType = attendance.getLeaveWork();
//            if (leaveWorkType != null) {
//                attendanceDays.put(leaveWorkType, attendanceDays.get(leaveWorkType) + 1);
//            }
//        }
//
//        return attendanceDays;
//    }
//
//    private boolean isLate(Attendance attendance) {
//
//        return attendance.getGoWork() == AttendanceType.TARDINESS;
//    }
//
//    private boolean isAbsent(Attendance attendance) {
//
//        return attendance.getGoWork() == AttendanceType.ABSENT
//                || attendance.getLeaveWork() == AttendanceType.ABSENT;
//    }
//}
