package kr.co.monitoringserver.controller.api.user;

import jakarta.validation.Valid;
import kr.co.monitoringserver.service.dtos.response.ResponseDTO;
import kr.co.monitoringserver.service.dtos.request.AdminRequestDTO;
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
    public ResponseDTO<?> update(@Valid @RequestBody AdminRequestDTO adminDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = adminService.validateHandling(bindingResult);

            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        adminService.update(adminDto);

        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }
}

