package kr.co.monitoringserver.controller.api.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendStatusResDTO;
import kr.co.monitoringserver.service.service.attendance.AttendanceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance_statuses")
@RequiredArgsConstructor
public class AttendanceStatusController {

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

    /** Get Attendance Status By Date Controller
     *
     */
//    @GetMapping("/date")
//    public ResponseFormat<List<AttendStatusResDTO.READ>> getAttendanceStatusByDate(
//            @RequestParam(name = "start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//            @RequestParam(name = "end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
//
//        return ResponseFormat.successData(
//                ErrorCode.SUCCESS_EXECUTE,
//                attendanceStatusService.getAttendanceStatusByDate(startDate, endDate)
//        );
//    }

    /** Update Attendance Status Controller
     *
     */
//    @PutMapping("/{attendance_status_id}")
//    public ResponseFormat<Void> updateAttendanceStatus(
//            @PathVariable(name = "attendance_status_id") Long attendanceStatusId,
//            @RequestBody AttendStatusReqDTO.UPDATE update) {
//
//        attendanceStatusService.updateAttendanceStatus(attendanceStatusId, update);
//
//        return ResponseFormat.successMessage(
//                ErrorCode.SUCCESS_EXECUTE,
//                "출석 상태 정보가 성공적으로 수정되었습니다"
//        );
//    }

    @DeleteMapping("/{attendance_status_id}")
    public ResponseFormat<Void> deleteAttendanceStatus(@PathVariable(name = "attendance_status_id") Long attendanceStatusId) {

        attendanceStatusService.deleteAttendanceStatus(attendanceStatusId);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                "출석 상태 정보가 성공적으로 삭제되었습니다"
        );
    }
}
