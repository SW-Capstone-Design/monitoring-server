package kr.co.monitoringserver.controller.user;

import kr.co.monitoringserver.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

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

    @GetMapping("/attendance/list")
    public String attendList(Model model, @PageableDefault(size=10, sort="date", direction = Sort.Direction.DESC) Pageable pageable,
                             LocalDate searchKeyword){

        if(searchKeyword == null) {
            model.addAttribute("lists", userService.attendList(pageable));
        }else{
            model.addAttribute("lists", userService.attendSearchList(searchKeyword, pageable));
        }

        return "user/attendance/inquire";
    }

    @GetMapping("/attendance/register")
    public String attendCreateForm() {

        return "user/attendance/register";
    }
}