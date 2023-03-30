package kr.co.monitoringserver.controller.api;


import kr.co.monitoringserver.dto.ResponseDto;
import kr.co.monitoringserver.service.model.Users;
import kr.co.monitoringserver.service.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody Users users) {
        usersService.join(users);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}