package kr.co.monitoringserver.controller.api.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendStatusResDTO;
import kr.co.monitoringserver.service.service.attendance.AttendanceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance_status")
@RequiredArgsConstructor
public class AttendanceStatusApiController {

    private final AttendanceStatusService attendanceStatusService;

    /** Create Attendance Status Controller
     *
     */
    @PostMapping
    public ResponseFormat<Void> createAttendanceStatus(@RequestBody @Validated AttendStatusReqDTO.CREATE create) {

        attendanceStatusService.createAttendanceStatus(create);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_CREATED,
                "출석 상태 정보가 성공적으로 생성되었습니다"
        );
    }

    /** Get Attendance Status By Attendance id Controller
     *
     */
    @GetMapping("/{attendance_id}")
    public ResponseFormat<List<AttendStatusResDTO.READ>> getAttendanceStatusByAttendanceId(
            @PathVariable(name = "attendance_id") Long attendanceId) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                attendanceStatusService.getAttendanceStatusByAttendanceId(attendanceId)
        );
    }

    /** Update Attendance Status Controller
     *
     */
    @PutMapping
    public ResponseFormat<Void> updateAttendanceStatus(@RequestBody AttendStatusReqDTO.UPDATE update) {

        attendanceStatusService.updateAttendanceStatus(update);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                "출석 상태 정보가 성공적으로 수정되었습니다"
        );
    }

    /** Delete Attendance Status Controller
     *
     */
    @DeleteMapping("/{attendance_status_id}")
    public ResponseFormat<Void> deleteAttendanceStatus(@PathVariable(name = "attendance_status_id") Long attendanceStatusId) {

        attendanceStatusService.deleteAttendanceStatus(attendanceStatusId);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                "출석 상태 정보가 성공적으로 삭제되었습니다"
        );
    }
}
