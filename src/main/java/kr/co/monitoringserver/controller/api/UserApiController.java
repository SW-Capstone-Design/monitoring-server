package kr.co.monitoringserver.controller.api;

import jakarta.validation.Valid;
import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.request.UserReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.dtos.response.ResDTO;
import kr.co.monitoringserver.service.service.UserService;
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

    /**
     * saveUser : 사용자정보를 Create하여 회원가입을 수행한다.
     */
    @PostMapping("/auth/joinProc")
    public ResDTO<?> saveUser(@Valid @RequestBody UserReqDTO userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return new ResDTO<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        userService.join(userDto);
        return new ResDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * updateUser : 사용자 본인의 회원정보를 Update 한다.
     */
    @PutMapping("/user")
    public ResDTO<?> updateUser(@Valid @RequestBody UserReqDTO userDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return new ResDTO<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        userService.update(userDto);

        Authentication authentication = authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getIdentity(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * Create UserAttendance Controller
     */
    @PostMapping("/api/v1/attendance/{user_identity}")
    public ResponseFormat<Void> createAttendance(@PathVariable(name = "user_identity") String userIdentity,
                                                 @RequestBody @Validated AttendanceReqDTO.CREATE create) {

        userService.createAttendance(userIdentity, create);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_CREATED,
                userIdentity + "님 출석 상태 정보가 성공적으로 생성되었습니다"
        );
    }

    /**
     * Get UserAttendance By userId Controller
     */
    /*
        @GetMapping("/api/v1/attendance/{user_id}")
        public ResponseFormat<List<AttendanceResDTO.READ>> getAttendanceByUserId(@PathVariable(name = "user_id") Long userId) {

            return ResponseFormat.successData(
                    ErrorCode.SUCCESS_EXECUTE,
                    userService.getAttendanceByUserId(userId)
            );
        }
    */

    /**
     * Get Latecomer UserAttendance By Date Controller
     */
    @GetMapping("/api/v1/attendance/latecomer")
    public ResponseFormat<List<AttendanceResDTO.READ>> getLatecomerByDate(@RequestParam("date") LocalDate date) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                userService.getLatecomerByDate(date)
        );
    }

    /**
     * Get Absentee UserAttendance By Date Controller
     */
    @GetMapping("/api/v1/attendance/absentee")
    public ResponseFormat<List<AttendanceResDTO.READ>> getAbsenteeByDate(@RequestParam("date") LocalDate date) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                userService.getAbsenteeByDate(date)
        );
    }

    /**
     * Update UserAttendance Controller
     */
    @PutMapping("/api/v1/attendance/{user_identity}")
    public ResponseFormat<Void> updateAttendance(@PathVariable(name = "user_identity") String userIdentity,
                                                 @RequestBody AttendanceReqDTO.UPDATE update) {

        userService.updateAttendance(userIdentity, update);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                userIdentity + "님 출석 상태 정보가 성공적으로 수정되었습니다"
        );
    }

    /**
     * Delete UserAttendance Controller
     */
    @DeleteMapping("/api/v1/attendance/{user_id}")
    public ResponseFormat<Void> deleteAttendance(@PathVariable(name = "user_id") Long userId) {

        userService.deleteAttendance(userId);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                "출석 상태 정보가 성공적으로 삭제되었습니다"
        );
    }
}