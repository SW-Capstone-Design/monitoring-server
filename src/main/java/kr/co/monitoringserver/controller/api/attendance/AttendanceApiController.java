package kr.co.monitoringserver.controller.api.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    /** Get User Attendance Records Controller
     *  특정 사용자의 출석 기록을 조회
     */


    /** Get User Attendance Records By Date Controller
     *  특정 일자의 모든 사용자의 출석 기록을 조회
     */


    /** Get User Attendance Records By Specific Period Controller
     *  특정 기간 동안의 모든 사용자의 출석 기록을 조회
     */


    /** Create Attendance Go-Work & Leave-Work Recording Controller
     *  특정 사용자의 출근 및 퇴근 기록을 등록
     */


    /** Update Attendance Records Controller
     *  등록된 출석 기록을 수정
     */


    /** Delete Attendance Records Controller
     *  등록된 출석 기록을 삭제
     */
    @DeleteMapping("/{attendance_id}")
    public ResponseFormat<Void> deleteAttendance(@PathVariable(name = "attendance_id") Long attendanceId) {

        attendanceService.deleteAttendance(attendanceId);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                "출석 정보가 성공적으로 삭제되었습니다"
        );
    }
}
