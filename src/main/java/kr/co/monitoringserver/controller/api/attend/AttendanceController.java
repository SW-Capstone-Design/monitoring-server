package kr.co.monitoringserver.controller.api.attend;

import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.service.attend.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public void createAttendance(@RequestBody @Validated AttendanceReqDTO.CREATE create) {

        attendanceService.createAttendance(create);
    }

    @GetMapping("/{attendance_id}")
    public AttendanceResDTO.READ getAttendanceById(@PathVariable(name = "attendance_id") Long attendanceId) {

        return attendanceService.getAttendanceById(attendanceId);
    }

    @PostMapping
    public void updateAttendance(AttendanceReqDTO.UPDATE update) {

        attendanceService.updateAttendance(update);
    }
}
