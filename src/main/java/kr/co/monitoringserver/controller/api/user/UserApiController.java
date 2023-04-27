package kr.co.monitoringserver.controller.api.user;


import jakarta.validation.Valid;
import kr.co.monitoringserver.service.dtos.request.UserReqDTO;
import kr.co.monitoringserver.service.dtos.response.UserResDTO;
import kr.co.monitoringserver.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationmanager;

    @PostMapping("/auth/joinProc")
    public UserResDTO<?> save(@Valid @RequestBody UserReqDTO userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return new UserResDTO<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        userService.join(userDto);
        return new UserResDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user")
    public UserResDTO<?> update(@Valid @RequestBody UserReqDTO userDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return new UserResDTO<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        userService.update(userDto);

        Authentication authentication = authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getIdentity(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new UserResDTO<Integer>(HttpStatus.OK.value(), 1);
    }
}