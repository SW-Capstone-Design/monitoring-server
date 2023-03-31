package kr.co.monitoringserver.controller;

import kr.co.monitoringserver.service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/index")
    public String IndexForm() {

        return "admin/index";
    }

    @GetMapping("/admin/info")
    public String list(Model model, @PageableDefault(size=10, sort="usersId", direction = Sort.Direction.ASC) Pageable pageable){
        model.addAttribute("lists", adminService.list(pageable));

        return "admin/info/list";
    }

    @GetMapping("/admin/info/{usersId}")
    public String findByUsersId(@PathVariable long usersId, Model model){
            model.addAttribute("list", adminService.detail(usersId));
            return "admin/info/detail";
    }
}
