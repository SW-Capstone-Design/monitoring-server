package kr.co.monitoringserver.controller.api;

import jakarta.validation.Valid;
import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
import kr.co.monitoringserver.service.dtos.request.AdminReqDTO;
import kr.co.monitoringserver.service.dtos.request.UserReqDTO;
import kr.co.monitoringserver.service.dtos.response.ResDTO;
import kr.co.monitoringserver.service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequiredArgsConstructor
public class AdminApiController {

    @Autowired
    private AdminService adminService;

    /**
     * updateUser : 회원정보를 Update 한다.
     */
    @PutMapping("/admin")
    public ResDTO<?> updateUser(@Valid @RequestBody AdminReqDTO adminDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = adminService.validateHandling(bindingResult);

            return new ResDTO<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        adminService.update(adminDto);

        return new ResDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * deleteUser : 회원정보를 Delete 한다.
     * 보완 필요 : 해당 유저의 UserAttendance 데이터가 남아있을 경우 회원탈퇴가 되지 않음.
     * 어떻게 처리할지 논의가 필요하다.
     */
    @DeleteMapping("/admin/info/delete")
    public void deleteUser(@RequestBody UserReqDTO userReqDTO){

        adminService.deleteUser(userReqDTO);

    }
}