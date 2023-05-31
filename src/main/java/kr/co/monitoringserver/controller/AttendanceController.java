package kr.co.monitoringserver.controller;

import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class AttendanceController {

    @Autowired
    UserAttendanceRepository userAttendanceRepository;

    @Autowired
    UserService userService;


    /**
     * userAttendList : 출결정보 조회 페이지로 매핑한다.
     */
    @GetMapping("/attendance/list")
    public String userAttendList(Model model, @PageableDefault(size=10, sort="attendance.date", direction = Sort.Direction.DESC) Pageable pageable,
                                 LocalDate searchKeyword, Principal principal){

        if (searchKeyword == null) {
            model.addAttribute("lists", userService.userAttendList(principal.getName(), pageable));
        } else {
            model.addAttribute("lists", userService.searchUserAttendList(searchKeyword, pageable));
        }

        return "user/attendance/inquire";
    }

    /**
     * userAttendConditionList : 출결정보 현황 페이지로 매핑한다.
     */
    @GetMapping("/attendance/list/condition")
    public String userAttendConditionList(Model model, Principal principal){

        model.addAttribute("work", userAttendanceRepository.countByUser_IdentityAndAttendance_GoWorkAndAttendance_LeaveWork(principal.getName(), AttendanceType.GO_WORK, AttendanceType.LEAVE_WORK));
        model.addAttribute("tardiness", userAttendanceRepository.countByUser_IdentityAndAttendance_GoWork(principal.getName(), AttendanceType.TARDINESS));
        model.addAttribute("earlyLeave", userAttendanceRepository.countByUser_IdentityAndAttendance_LeaveWork(principal.getName(), AttendanceType.EARLY_LEAVE));
        model.addAttribute("absent", userAttendanceRepository.countByUser_IdentityAndAttendance_LeaveWork(principal.getName(), AttendanceType.ABSENT));

        return "user/attendance/condition";
    }

    /**
     * attendCreateForm : 출결정보 생성 폼이다.
     */
    @GetMapping("/attendance/register")
    public String attendCreateForm() {

        return "user/attendance/register";
    }
}
