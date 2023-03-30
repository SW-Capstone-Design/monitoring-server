package kr.co.monitoringserver.controller;

import jakarta.validation.Valid;
import kr.co.monitoringserver.service.model.request.UserRequest;
import kr.co.monitoringserver.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void signUp(@RequestBody @Valid UserRequest userRequest) {
        userService.signUp(userRequest);
    }
}
