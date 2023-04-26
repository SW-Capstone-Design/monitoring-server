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

    @GetMapping("/{user_id}")
    public String findByUserId(@PathVariable long user_id, Model model) {
        model.addAttribute("list", attendanceService.getAttendanceRecordsByUserId(user_id));
        return "user/attendance/inquire";
    }

}
