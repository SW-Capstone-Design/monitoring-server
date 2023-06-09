package kr.co.monitoringserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    /**
     * start : 최초 사이트 접속시 로그인 페이지를 매핑한다.
     */
    @GetMapping("/")
    public String start(Principal principal) {

        if(principal != null){
            return "redirect:/index";
        } else {
            return "user/loginForm";
        }
    }

    /**
     * index : 로그인 성공시 인덱스를 매핑한다.
     */
    @GetMapping("/index")
    public String index() {

        return "user/index";
    }

    /**
     * loginForm : 로그인 폼을 매핑한다.
     */
    @GetMapping("/auth/loginForm")
    public String loginForm() {

        return "user/loginForm";
    }

    /**
     * joinForm : 회원가입 폼을 매핑한다.
     */
    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    /**
     * updateForm : 사용자의 회원정보 수정 폼을 매핑한다.
     */
    @GetMapping("/user/updateForm")
    public String updateForm() {

        return "user/updateForm";
    }
}