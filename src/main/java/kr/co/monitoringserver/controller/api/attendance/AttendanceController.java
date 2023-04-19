package kr.co.monitoringserver.controller.api.attendance;

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
    @PostMapping
    public void createAttendance(@RequestBody @Validated AttendanceReqDTO.CREATE create) {

        attendanceService.createAttendance(create);
    }

    /** Get Attendance By attendanceId Controller
     *
     */
    @GetMapping("/{attendance_id}")
    public AttendanceResDTO.READ getAttendanceById(@PathVariable(name = "attendance_id") Long attendanceId) {

        return attendanceService.getAttendanceById(attendanceId);
    }
}
