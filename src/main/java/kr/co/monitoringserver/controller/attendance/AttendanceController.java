package kr.co.monitoringserver.controller.attendance;

import kr.co.monitoringserver.service.service.attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping("attendance/{user_id}")
    public String findByUserId(@PathVariable(name = "user_id") long userId, Model model) {
        model.addAttribute("list", attendanceService.getAttendanceRecordsByUserId(userId));
        return "user/attendance/inquire";
    }

}
