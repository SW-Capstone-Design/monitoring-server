package kr.co.monitoringserver.controller.attendance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceStatusController {

    @GetMapping("/attendance/register")
    public String createForm() {

        return "user/attendance/register";
    }
}
