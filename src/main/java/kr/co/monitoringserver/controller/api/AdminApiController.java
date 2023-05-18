package kr.co.monitoringserver.controller.api;

import jakarta.validation.Valid;
import kr.co.monitoringserver.service.dtos.request.AdminReqDTO;
import kr.co.monitoringserver.service.dtos.request.IndexNotificationReqDTO;
import kr.co.monitoringserver.service.dtos.request.UserReqDTO;
import kr.co.monitoringserver.service.dtos.response.ResponseDto;
import kr.co.monitoringserver.service.service.AdminService;
import kr.co.monitoringserver.service.service.UserService;
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

    @Autowired
    private UserService userService;

    /**
     * saveUser : 사용자정보를 Create하여 회원가입을 수행한다.
     */
    @PostMapping("/admin/joinProc")
    public ResponseDto<?> saveUser(@Valid @RequestBody UserReqDTO userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        userService.join(userDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * updateUser : 회원정보를 Update 한다.
     */
    @PutMapping("/admin")
    public ResponseDto<?> updateUser(@Valid @RequestBody AdminReqDTO adminDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = adminService.validateHandling(bindingResult);

            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        adminService.update(adminDto);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * deleteUser : 회원정보를 Delete 한다.
     */
    @DeleteMapping("/admin/info/delete")
    public void deleteUser(@RequestBody UserReqDTO userReqDTO){

        adminService.deleteUser(userReqDTO);

    }

    @DeleteMapping("/admin/alert/delete")
    public void deleteAlert(@RequestBody IndexNotificationReqDTO indexNotificationReqDTO){

        adminService.deleteAlert(indexNotificationReqDTO);
    }

    @DeleteMapping("/admin/alert/delete/ten")
    public void deleteAlertTopTen(){

        adminService.deleteAlertTopTen();
    }

}