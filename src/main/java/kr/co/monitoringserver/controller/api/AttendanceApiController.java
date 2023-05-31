package kr.co.monitoringserver.controller.api;

import kr.co.monitoringserver.infra.global.model.ResponseFormat;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance")
public class AttendanceApiController {
    
    private final AttendanceService attendanceService;

    /**
     * Create And Save User Clock In Controller
     */
    @PostMapping("/clock_in/{user_identity}")
    public ResponseFormat<Void> createAndSaveUserClockIn(@PathVariable(name = "user_identity") String userIdentity) {

        attendanceService.createAndSaveUserClockIn(userIdentity);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_CREATED,
                userIdentity + "님 출근이 성공적으로 저장되었습니다"
        );
    }

    /**
     * Update And Save User Clock Out Controller
     */
    @PutMapping("/clock_out/{user_identity}")
    public ResponseFormat<Void> updateAndSaveUserClockOut(@PathVariable(name = "user_identity") String userIdentity,
                                                          @RequestParam(name = "date") LocalDate date) {

        attendanceService.updateAndSaveUserClockOut(userIdentity, date);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_CREATED,
                userIdentity + "님 퇴근이 성공적으로 저장되었습니다"
        );
    }

    /**
     * Get UserAttendance By User Identity Controller
     */
    @GetMapping()
    public ResponseFormat<Page<AttendanceResDTO.READ>> getAttendanceByUserIdentity(Principal principal,
                                                                                   @PageableDefault Pageable pageable) {

        return ResponseFormat.successData(
                ResponseStatus.SUCCESS_EXECUTE,
                attendanceService.getAttendanceByUserIdentity(principal, pageable)
        );
    }

    /**
     * Get Latecomer UserAttendance By Date Controller
     */
    @GetMapping("/latecomer")
    public ResponseFormat<Page<AttendanceResDTO.READ>> getLatecomerByDate(@RequestParam("date") LocalDate date,
                                                                          @PageableDefault Pageable pageable) {

        return ResponseFormat.successData(
                ResponseStatus.SUCCESS_EXECUTE,
                attendanceService.getLatecomerByDate(date, pageable)
        );
    }

    /**
     * Get Absentee UserAttendance By Date Controller
     */
    @GetMapping("/absentee")
    public ResponseFormat<Page<AttendanceResDTO.READ>> getAbsenteeByDate(@RequestParam("date") LocalDate date,
                                                                         @PageableDefault Pageable pageable) {

        return ResponseFormat.successData(
                ResponseStatus.SUCCESS_EXECUTE,
                attendanceService.getAbsenteeByDate(date, pageable)
        );
    }

    /**
     * Update UserAttendance Controller
     */
    @PutMapping("/{user_identity}")
    public ResponseFormat<Void> updateAttendance(@PathVariable(name = "user_identity") String userIdentity,
                                                 @RequestBody AttendanceReqDTO.UPDATE update) {

        attendanceService.updateAttendance(userIdentity, update);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_EXECUTE,
                userIdentity + "님 출석 상태 정보가 성공적으로 수정되었습니다"
        );
    }

    /**
     * Delete UserAttendance Controller
     */
    @DeleteMapping("/{user_identity}")
    public ResponseFormat<Void> deleteAttendance(@PathVariable(name = "user_identity") String userIdentity,
                                                 @RequestParam(name = "date") LocalDate date) {

        attendanceService.deleteAttendance(userIdentity, date);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_EXECUTE,
                "출석 상태 정보가 성공적으로 삭제되었습니다"
        );
    }
}
