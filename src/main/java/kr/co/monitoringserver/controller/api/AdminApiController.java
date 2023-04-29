package kr.co.monitoringserver.controller.api;

import jakarta.validation.Valid;
import kr.co.monitoringserver.service.dtos.response.ResponseDto;
import kr.co.monitoringserver.service.dtos.request.AdminRequestDto;
import kr.co.monitoringserver.service.service.AdminService;
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
    public ResponseDto<?> update(@Valid @RequestBody AdminRequestDto adminDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = adminService.validateHandling(bindingResult);

            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        adminService.update(adminDto);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}