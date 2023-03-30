package kr.co.monitoringserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping(value = {"/admin/", "/admin"})
    public String IndexForm() {

        return "admin/index";
    }
}
