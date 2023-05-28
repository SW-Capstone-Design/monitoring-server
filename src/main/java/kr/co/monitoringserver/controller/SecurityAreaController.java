package kr.co.monitoringserver.controller;

import kr.co.monitoringserver.service.service.SecurityAreaLocationService;
import kr.co.monitoringserver.service.service.SecurityAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SecurityAreaController {

    @Autowired
    SecurityAreaService securityAreaService;

    @Autowired
    SecurityAreaLocationService securityAreaLocationService;

    /**
     * securityAreaCreateForm : SecurityArea Create 폼으로 매핑한다.
     */
    @GetMapping("/admin/area/create")
    public String securityAreaCreateForm() {

        return "admin/securityArea/create";
    }

    /**
     * securityAreaList : 보안구역 목록 조회 페이지로 매핑하며 Page 객체를 반환한다.
     */
    @GetMapping("/admin/area/info")
    public String securityAreaList(Model model, @PageableDefault(size=10, sort="id", direction = Sort.Direction.ASC) Pageable pageable){

        model.addAttribute("lists", securityAreaService.getSecurityArea(pageable));

        return "admin/securityArea/list";
    }

    /**
     * securityAreaFindByID : Id를 통해 SecurityArea를 조회하여 Model 객체를 Update, Delete Form에 매핑한다.
     */
    @GetMapping("/admin/area/info/{security_area_id}")
    public String securityAreaFindByID(@PathVariable(name = "security_area_id") Long securityAreaId, Model model){

        model.addAttribute("list", securityAreaService.securityAreaDetail(securityAreaId));

        return "admin/securityArea/detail";
    }

    /**
     * securityAccessList : securityArea 접근 기록을 조회한다.
     */
    @GetMapping("/admin/area/accessInfo")
    public String securityAccessList(Model model, @PageableDefault(size=10, sort="accessTime", direction = Sort.Direction.DESC) Pageable pageable){

        model.addAttribute("lists", securityAreaLocationService.listSecurityAccessLog(pageable));

        return "admin/securityArea/logList";
    }
}
