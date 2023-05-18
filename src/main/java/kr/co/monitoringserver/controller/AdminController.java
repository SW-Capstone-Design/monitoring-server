package kr.co.monitoringserver.controller;

import kr.co.monitoringserver.persistence.repository.IndexNotificationRepository;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.service.AdminService;
import kr.co.monitoringserver.service.service.UserService;
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
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private IndexNotificationRepository indexNotificationRepository;

    /**
     * IndexForm : 관리자페이지 홈페이지로 매핑한다.
     */
    @GetMapping("/admin/index")
    public String IndexForm() {

        return "admin/index";
    }

    /**
     * joinForm : 회원가입 폼을 매핑한다.
     */
    @GetMapping("/admin/joinForm")
    public String joinForm() {

        return "admin/info/joinForm";
    }

    /**
     * list : 유저정보를 Select하여 조회가 가능하다.
     * 페이징 보완 필요, size는 추후 변경할 예정 
     */
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

    /**
     * infoFindByUserId : Model 객체를 얹어 각 유저의 회원정보 수정 페이지로 매핑한다.
     */
    @GetMapping("/admin/info/{userId}")
    public String infoFindByUserId(@PathVariable long userId, Model model){
            model.addAttribute("list", adminService.detail(userId));
            return "admin/info/detail";
    }

    /**
     * attendList : 회원출결정보를 Select 한다.
     */
    @GetMapping("/admin/attendance/list")
    public String attendList(Model model, @PageableDefault(size=10, sort="attendance.date", direction = Sort.Direction.DESC) Pageable pageable,
                             LocalDate searchKeyword){

        if(searchKeyword == null) {
            model.addAttribute("lists", adminService.attendList(pageable));
        }else{
            model.addAttribute("lists", adminService.searchAttendList(searchKeyword, pageable));
        }

        return "admin/attendance/inquire";
    }

    /**
     * attendFindByUserId : Model 객체를 얹어 각 유저의 회원정보 수정 페이지로 매핑한다.
     */
    @GetMapping("/admin/attendance/list/{userId}/{date}")
    public String attendFindByUserId(@PathVariable Long userId, @PathVariable LocalDate date, Model model){
        model.addAttribute("list", adminService.attendDetail(userId, date));
        return "admin/attendance/updateForm";
    }

    /**
     * tardinessAttendList : 지각한 회원을 Select 한다.
     */
    @GetMapping("/admin/attendance/list/tardiness")
    public String tardinessAttendList(Model model, @PageableDefault(size=10, sort="user.userId", direction = Sort.Direction.ASC) Pageable pageable
            , LocalDate date, LocalDate searchKeyword){

        if(searchKeyword == null) {
            model.addAttribute("lists", userService.tardinessAttendList(AttendanceType.TARDINESS, date, pageable));
        }else{
            model.addAttribute("lists", userService.searchTardinessAttendList(AttendanceType.TARDINESS, pageable, searchKeyword));
        }

        return "admin/attendance/inquireTardiness";
    }

    @GetMapping("/admin/attendance/list/earlyLeave")
    public String earlyLeaveAttendList(Model model, @PageableDefault(size=10, sort="user.userId", direction = Sort.Direction.ASC) Pageable pageable
            , LocalDate date, LocalDate searchKeyword){

        if(searchKeyword == null) {
            model.addAttribute("lists", userService.earlyLeaveAttendList(AttendanceType.EARLY_LEAVE, date, pageable));
        }else{
            model.addAttribute("lists", userService.searchEarlyLeaveAttendList(AttendanceType.EARLY_LEAVE, pageable, searchKeyword));
        }

        return "admin/attendance/inquireEarlyLeave";
    }

    /**
     * searchAlert : Beacon 배터리 잔량 알림 조회
     */
    @GetMapping("/admin/alert")
    public String searchAlert(Model model, @PageableDefault(size=10, sort="indexAlertTime", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("alerts", adminService.alertList(pageable));
        model.addAttribute("count", indexNotificationRepository.countBy());

        return "admin/alertList";
    }
}
