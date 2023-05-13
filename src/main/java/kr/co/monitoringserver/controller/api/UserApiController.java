package kr.co.monitoringserver.controller.api;

import jakarta.validation.Valid;
import kr.co.monitoringserver.service.dtos.request.UserReqDTO;
import kr.co.monitoringserver.service.dtos.response.ResponseDto;
import kr.co.monitoringserver.service.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    private final AuthenticationManager authenticationmanager;

    /**
     * saveUser : 사용자정보를 Create하여 회원가입을 수행한다.
     */
    @PostMapping("/auth/joinProc")
    public ResponseDto<?> saveUser(@Valid @RequestBody UserReqDTO userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        userService.join(userDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * updateUser : 사용자 본인의 회원정보를 Update 한다.
     */
    @PutMapping("/user")
    public ResponseDto<?> updateUser(@Valid @RequestBody UserReqDTO userDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        userService.update(userDto);

        Authentication authentication = authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getIdentity(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}