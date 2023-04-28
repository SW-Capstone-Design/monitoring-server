//package kr.co.monitoringserver.controller.api.attendance;
//
//import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
//import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
//import kr.co.monitoringserver.service.dtos.request.UserAttendanceReqDTO;
//import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
//import kr.co.monitoringserver.service.service.attendance.AttendanceService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/attendance_status")
//@RequiredArgsConstructor
//public class AttendanceApiController {
//
//    private final AttendanceService attendanceService;
//
//    /**
//     * Create UserAttendance Status Controller
//     */
//    @PostMapping("/{user_id}")
//    public ResponseFormat<Void> createAttendanceStatus(@PathVariable(name = "user_id") Long userId,
//                                                       @RequestBody @Validated UserAttendanceReqDTO.CREATE create) {
//
//        attendanceService.createAttendanceStatus(userId, create);
//
//        return ResponseFormat.successMessage(
//                ErrorCode.SUCCESS_CREATED,
//                "출석 상태 정보가 성공적으로 생성되었습니다"
//        );
//    }
//
//    /**
//     * Get UserAttendance Status By id Controller
//     */
//    @GetMapping("/{user_id}")
//    public ResponseFormat<AttendanceResDTO.READ> getAttendanceStatusByUserId(
//            @PathVariable(name = "user_id") Long userId) {
//
//        return ResponseFormat.successData(
//                ErrorCode.SUCCESS_EXECUTE,
//                attendanceService.getAttendanceStatusById(userId)
//        );
//    }
//
//    /**
//     * Get Tardiness User UserAttendance Status By Date Controller
//     */
//    @GetMapping("/tardiness")
//    public ResponseFormat<List<AttendanceResDTO.READ>> getTardinessUserByDate(@RequestParam("date") LocalDate date) {
//
//        return ResponseFormat.successData(
//                ErrorCode.SUCCESS_EXECUTE,
//                attendanceService.getTardinessUserByDate(date)
//        );
//    }
//
//    /**
//     * Get Absent User UserAttendance Status By Date Controller
//     */
//    @GetMapping("/absent")
//    public ResponseFormat<List<AttendanceResDTO.READ>> getAbsentUserByDate(@RequestParam("date") LocalDate date) {
//
//        return ResponseFormat.successData(
//                ErrorCode.SUCCESS_EXECUTE,
//                attendanceService.getAbsentUserByDate(date)
//        );
//    }
//
//    /**
//     * Update UserAttendance Status Controller
//     */
//    @PutMapping("/{}")
//    public ResponseFormat<Void> updateAttendanceStatus(@PathVariable(name = "user_id") Long userId,
//                                                       @RequestBody UserAttendanceReqDTO.UPDATE update) {
//
//        attendanceService.updateAttendanceStatus(userId, update);
//
//        return ResponseFormat.successMessage(
//                ErrorCode.SUCCESS_EXECUTE,
//                "출석 상태 정보가 성공적으로 수정되었습니다"
//        );
//    }
//
//    /**
//     * Delete UserAttendance Status Controller
//     */
//    @DeleteMapping("/{attendance_status_id}")
//    public ResponseFormat<Void> deleteAttendanceStatus(@PathVariable(name = "attendance_status_id") Long attendanceStatusId) {
//
//        attendanceService.deleteAttendanceStatus(attendanceStatusId);
//
//        return ResponseFormat.successMessage(
//                ErrorCode.SUCCESS_EXECUTE,
//                "출석 상태 정보가 성공적으로 삭제되었습니다"
//        );
//    }
//}
