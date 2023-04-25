package kr.co.monitoringserver.controller.api.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    /**
     * Get User Attendance Records Controller
     * 특정 사용자의 출석 기록을 조회
     */
    @GetMapping("/{user_id}")
    public ResponseFormat<List<AttendanceResDTO.READ>> getAttendanceRecordsByUserId(@PathVariable(name = "user_id") Long userId) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                attendanceService.getAttendanceRecordsByUserId(userId)
        );
    }


    /**
     * Get User Attendance Records By Date Controller
     * 특정 일자의 모든 사용자의 출석 기록을 조회
     */
    @GetMapping("/date")
    public ResponseFormat<List<AttendanceResDTO.READ>> getAllUserAttendanceRecordsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                attendanceService.getAllUserAttendanceRecordsByDate(date)
        );
    }


    /**
     * Get User Attendance Records By Specific Period Controller
     * 특정 기간 동안의 모든 사용자의 출석 기록을 조회
     */
    @GetMapping("/period")
    public ResponseFormat<List<AttendanceResDTO.READ>> getAllUserAttendanceRecordsByPeriod(
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                attendanceService.getAllUserAttendanceRecordsByPeriod(startDate, endDate)
        );
    }
}