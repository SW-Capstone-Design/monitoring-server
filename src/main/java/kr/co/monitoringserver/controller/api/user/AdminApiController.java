package kr.co.monitoringserver.controller.api.user;

import jakarta.validation.Valid;
import kr.co.monitoringserver.service.dtos.response.UserResDTO;
import kr.co.monitoringserver.service.dtos.request.AdminReqDTO;
import kr.co.monitoringserver.service.service.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class AdminApiController {
    @Autowired
    private AdminService adminService;

    @PutMapping("/admin")
    public UserResDTO<?> update(@Valid @RequestBody AdminReqDTO adminDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = adminService.validateHandling(bindingResult);

            return new UserResDTO<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        adminService.update(adminDto);

        return new UserResDTO<Integer>(HttpStatus.OK.value(), 1);
    }
}

