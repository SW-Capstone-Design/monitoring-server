package kr.co.monitoringserver.service.service.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import kr.co.monitoringserver.persistence.repository.AttendanceStatusRepository;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendStatusResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.mappers.AttendanceStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceStatusService {

    /**
     * 출석 상태 서비스
     * 출석 기록이 생성될 때마다 호출되어 출석 상태를 업데이트하는 역할
     */

    private final AttendanceStatusRepository attendanceStatusRepository;

    private final AttendanceRepository attendanceRepository;

    private final AttendanceStatusMapper attendanceStatusMapper;

    /**
     * Create Attendance Status Service
     */
    @Transactional
    public void createAttendStatus(AttendStatusReqDTO.CREATE create) {

        AttendanceStatus getAttendanceStatus = attendanceStatusRepository.findById(create.getAttendanceStatusId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE_STATUS));

        List<Attendance> attendances = getAttendanceStatus.getAttendances();

        for (Attendance att : attendances) {
            calculateAttendanceStatus(att.getEnterTime(), att.getLeaveTime());
        }

        AttendanceStatus attendanceStatus = attendanceStatusMapper.toAttendStatusEntity(create, attendances);

        attendanceStatusRepository.save(attendanceStatus);
    }

    /** Get Attendance Status By Attendance id Service
     *
     */
    public List<AttendStatusResDTO.READ> getAttendStatusByAttendanceId(Long attendanceId) {

        final Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE));

        return attendanceStatusRepository.findByAttendance(attendance)
                .stream()
                .map(attendanceStatusMapper::toAttendStatusReadDto)
                .collect(Collectors.toList());
    }


    private AttendanceType calculateAttendanceStatus(LocalTime enterTime, LocalTime leaveTime) {

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












    /** Get Attendance Status By Date Service
     *
     */
//    public List<AttendStatusResDTO.READ> getAttendanceStatusByDate(LocalDate startDate,
//                                                                   LocalDate endDate) {
//
//        List<AttendanceStatus> attendanceStatuses = attendanceStatusRepository.findByCreatedAtBetween(
//                startDate.atStartOfDay(),
//                endDate.plusDays(1).atStartOfDay());
//
//        return attendanceStatuses
//                .stream()
//                .map(attendanceStatusMapper::toAttendStatusReadDto)
//                .collect(Collectors.toList());
//    }

    /** Update Attendance Status Service
     *
     */
//    @Transactional
//    public void updateAttendanceStatus(Long attendanceStatusId,
//                                       AttendStatusReqDTO.UPDATE update) {
//
//        AttendanceStatus attendanceStatus = attendanceStatusRepository.findById(attendanceStatusId)
//                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE));
//
//        AttendanceStatus updatedStatus = attendanceStatusMapper.toUpdatedAttendStatusEntity(attendanceStatus, update);
//
//        final AttendanceType attendanceType = calculateAttendanceStatus(
//                updatedStatus.getEnterTime(),
//                updatedStatus.getLeaveTime());
//
//        updatedStatus.updateAttendanceType(attendanceType);
//    }

    /**
     * Delete Attendance Status By id Service
     */
    @Transactional
    public void deleteAttendanceStatus(Long attendanceStatusId) {

        AttendanceStatus attendanceStatus = attendanceStatusRepository.findById(attendanceStatusId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE));

        attendanceStatusRepository.delete(attendanceStatus);
    }
}
