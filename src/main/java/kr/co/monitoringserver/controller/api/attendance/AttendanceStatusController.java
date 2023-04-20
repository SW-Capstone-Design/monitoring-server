package kr.co.monitoringserver.controller.api.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
import kr.co.monitoringserver.service.service.attendance.AttendanceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendances_status")
@RequiredArgsConstructor
public class AttendanceStatusController {

    private final AttendanceStatusService attendanceStatusService;

    /** Create Attendance Status Controller
     *
     */
    @PostMapping
    public ResponseFormat<Void> createAttendanceStatus(AttendStatusReqDTO.CREATE create) {

        attendanceStatusService.createAttendanceStatus(create);
        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_CREATED,
                "출석 상태 정보가 성공적으로 생성되었습니다"
        );
    }

}
