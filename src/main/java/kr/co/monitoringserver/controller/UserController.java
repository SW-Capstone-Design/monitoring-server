package kr.co.monitoringserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {

        return "user/loginForm";
    }

    @Controller
    public class AdminController {
        @GetMapping("/auth/beaconForm")
        public String beaconForm() {

            return "user/beaconForm";
        }
    }
}