package kr.co.monitoringserver.controller.user;

import kr.co.monitoringserver.service.service.user.AdminService;
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
    public String list(Model model, @PageableDefault(size=3, sort="userId", direction = Sort.Direction.ASC) Pageable pageable,
                       String searchKeyword){

        if(searchKeyword == null) {
            model.addAttribute("lists", adminService.list(pageable));
        }else{
            model.addAttribute("lists", adminService.userSearchList(searchKeyword, pageable));
        }

        return "admin/info/list";
    }

    @GetMapping("/admin/info/{userId}")
    public String findByUserId(@PathVariable long userId, Model model){
            model.addAttribute("list", adminService.detail(userId));
            return "admin/info/detail";
    }
}