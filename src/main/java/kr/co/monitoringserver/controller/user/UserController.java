package kr.co.monitoringserver.controller.user;

import kr.co.monitoringserver.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {

        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {

        return "user/updateForm";
    }

    @GetMapping("attendance/{user_id}")
    public String findByUserId(@PathVariable(name = "user_id") long userId, Model model) {
        model.addAttribute("list", userService.getAttendanceByUserId(userId));
        return "user/attendance/inquire";
    }

    @GetMapping("/attendance/register")
    public String attendCreateForm() {

        return "user/attendance/register";
    }
}