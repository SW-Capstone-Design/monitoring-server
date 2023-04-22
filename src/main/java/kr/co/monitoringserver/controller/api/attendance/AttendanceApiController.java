package kr.co.monitoringserver.controller.api.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    /** Get User Attendance Records Controller
     *  특정 사용자의 출석 기록을 조회
     */
    @GetMapping("/users/{user_id}")
    public ResponseFormat<List<AttendanceResDTO.READ>> getAttendanceRecordsByUserId(@PathVariable(name = "user_id") Long userId) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                attendanceService.getAttendanceRecordsByUserId(userId)
        );
    }


    /** Get User Attendance Records By Date Controller
     *  특정 일자의 모든 사용자의 출석 기록을 조회
     */
    @GetMapping("/users/{date}")
    public ResponseFormat<List<AttendanceResDTO.READ>> getAllUserAttendanceRecordsByDate(@PathVariable(name = "date") LocalDate date) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                attendanceService.getAllUserAttendanceRecordsByDate(date)
        );
    }


    /** Get User Attendance Records By Specific Period Controller
     *  특정 기간 동안의 모든 사용자의 출석 기록을 조회
     */
    @GetMapping("/{start_date}/{end_date}")
    public ResponseFormat<List<AttendanceResDTO.READ>> getAllUserAttendanceRecordsByPeriod(
            @PathVariable(name = "start_date") LocalDate startDate,
            @PathVariable(name = "end_date") LocalDate endDate) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                attendanceService.getAllUserAttendanceRecordsByPeriod(startDate, endDate)
        );
    }


    /** Create Attendance Go-Work & Leave-Work Recording Controller
     *  특정 사용자의 출근 및 퇴근 기록을 등록
     */


    /** Update Attendance Records Controller
     *  등록된 출석 기록을 수정
     */


    /** Delete Attendance Records Controller
     *  등록된 출석 기록을 삭제
     */
}
