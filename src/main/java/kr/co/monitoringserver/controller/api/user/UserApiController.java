package kr.co.monitoringserver.controller.api.user;


import jakarta.validation.Valid;
import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.request.UserAttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.request.UserReqDTO;
import kr.co.monitoringserver.service.dtos.response.UserAttendanceResDTO;
import kr.co.monitoringserver.service.dtos.response.UserResDTO;
import kr.co.monitoringserver.service.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserApiController {

    private final UserService userService;

    private final AuthenticationManager authenticationmanager;

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



    /**
     * Create UserAttendance Status Controller
     */
    @PostMapping("/api/v1/attendance_status/{user_identity}")
    public ResponseFormat<Void> createAttendance(@PathVariable(name = "user_identity") String userIdentity,
                                                 @RequestBody @Validated UserAttendanceReqDTO.CREATE create) {

        userService.createAttendance(userIdentity, create);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_CREATED,
                "출석 상태 정보가 성공적으로 생성되었습니다"
        );
    }

    /**
     * Get UserAttendance Status By userId Controller
     */
    @GetMapping("/api/v1/attendance_status/{user_id}")
    public ResponseFormat<List<UserAttendanceResDTO.READ>> getAttendanceByUserId(@PathVariable(name = "user_id") Long userId) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                userService.getAttendanceByUserId(userId)
        );
    }


    /**
     * Get Tardiness User UserAttendance Status By Date Controller
     */
    @GetMapping("/api/v1/attendance_status/tardiness")
    public ResponseFormat<List<UserAttendanceResDTO.READ>> getTardinessUserByDate(@RequestParam("date") LocalDate date) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                userService.getTardinessUserByDate(date)
        );
    }

    /**
     * Get Absent User UserAttendance Status By Date Controller
     */
    @GetMapping("/api/v1/attendance_status/absent")
    public ResponseFormat<List<UserAttendanceResDTO.READ>> getAbsentUserByDate(@RequestParam("date") LocalDate date) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                userService.getAbsentUserByDate(date)
        );
    }

    /**
     * Update UserAttendance Status Controller
     */
    @PutMapping("/api/v1/attendance_status/{user_identity}")
    public ResponseFormat<Void> updateAttendance(@PathVariable(name = "user_identity") String userIdentity,
                                                 @RequestBody UserAttendanceReqDTO.UPDATE update) {

        userService.updateAttendance(userIdentity, update);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                "출석 상태 정보가 성공적으로 수정되었습니다"
        );
    }

    /**
     * Delete UserAttendance Status Controller
     */
    @DeleteMapping("/api/v1/attendance_status/{user_id}")
    public ResponseFormat<Void> deleteAttendance(@PathVariable(name = "user_id") Long userId) {

        userService.deleteAttendance(userId);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                "출석 상태 정보가 성공적으로 삭제되었습니다"
        );
    }
}