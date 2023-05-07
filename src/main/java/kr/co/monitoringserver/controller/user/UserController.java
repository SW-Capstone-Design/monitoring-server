package kr.co.monitoringserver.controller.user;

import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
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
public class UserController {

    @Autowired
    UserService userService;

    /**
     * joinForm : 회원가입 폼을 매핑한다.
     */
    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    /**
     * loginForm : 로그인 폼을 매핑한다.
     */
    @GetMapping("/auth/loginForm")
    public String loginForm() {

        return "user/loginForm";
    }

    /**
     * updateForm : 사용자의 회원정보 수정 폼을 매핑한다.
     */
    @GetMapping("/user/updateForm")
    public String updateForm() {

        return "user/updateForm";
    }

    /**
     * attendList : 출결정보 조회 페이지로 매핑한다.
     */
    @GetMapping("/attendance/list/{user_id}")
    public String userAttendList(Model model, @PageableDefault(size=10, sort="attendance.date", direction = Sort.Direction.DESC) Pageable pageable,
                             @PathVariable(name = "user_id") Long userId
                            ,LocalDate searchKeyword){

        if(searchKeyword == null) {
            model.addAttribute("lists", userService.userAttendList(userId, pageable));
        }else{
            model.addAttribute("lists", userService.searchUserAttendList(searchKeyword, pageable));
        }

        return "user/attendance/inquire";
    }

    /**
     * attendCreateForm : 출결정보 생성 폼이다.
     * 보완 필요 : 블루투스가 연결된 상태로 버튼을 클릭했을 때만 출석이 인정되어야 함.
     * 퇴근 여부는 어떻게 처리할 것인지 논의가 필요하다.
     */
    @GetMapping("/attendance/register")
    public String attendCreateForm() {

        return "user/attendance/register";
    }
}