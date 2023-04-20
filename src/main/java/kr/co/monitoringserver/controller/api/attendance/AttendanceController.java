package kr.co.monitoringserver.controller.api.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    /** Create Attendance Controller
     *
     */
    @PostMapping("/{user_id}")
    public ResponseFormat<Void> createAttendance(@RequestBody @Validated AttendanceReqDTO.CREATE create,
                                                 @PathVariable(name = "user_id") Long userId) {

        attendanceService.createAttendance(create, userId);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_CREATED,
                "출석 정보가 성공적으로 생성되었습니다"
        );
    }

    /** Get Attendance By attendanceId Controller
     *
     */
    @GetMapping("/{attendance_id}")
    public ResponseFormat<AttendanceResDTO.READ> getAttendanceById(@PathVariable(name = "attendance_id")
                                                                       Long attendanceId) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                attendanceService.getAttendanceById(attendanceId)
        );
    }
}
