package kr.co.monitoringserver.controller.api;

import jakarta.validation.Valid;
import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.request.UserReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.dtos.response.ResponseDto;
import kr.co.monitoringserver.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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



    /**
     * Create UserAttendance Controller
     */
    @PostMapping("/attendance/{user_identity}")
    public ResponseFormat<Void> createAttendance(@PathVariable(name = "user_identity") String userIdentity,
                                                 @RequestBody @Validated AttendanceReqDTO.CREATE create) {

        userService.createAttendance(userIdentity, create);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_CREATED,
                userIdentity + "님 출석 상태 정보가 성공적으로 생성되었습니다"
        );
    }

    /**
     * Get UserAttendance By User Identity Controller
     */
    @GetMapping("/attendance/{user_identity}")
    public ResponseFormat<Page<AttendanceResDTO.READ>> getAttendanceByUserIdentity(@PathVariable(name = "user_identity") String userIdentity,
                                                                             @PageableDefault Pageable pageable) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                userService.getAttendanceByUserIdentity(userIdentity, pageable)
        );
    }

    /**
     * Get Latecomer UserAttendance By Date Controller
     */
    @GetMapping("/api/v1/attendance/latecomer")
    public ResponseFormat<Page<AttendanceResDTO.READ>> getLatecomerByDate(@RequestParam("date") LocalDate date,
                                                                          @PageableDefault Pageable pageable) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                userService.getLatecomerByDate(date, pageable)
        );
    }

    /**
     * Get Absentee UserAttendance By Date Controller
     */
    @GetMapping("/attendance/absentee")
    public ResponseFormat<Page<AttendanceResDTO.READ>> getAbsenteeByDate(@RequestParam("date") LocalDate date,
                                                                         @PageableDefault Pageable pageable) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                userService.getAbsenteeByDate(date, pageable)
        );
    }

    /**
     * Update UserAttendance Controller
     */
    @PutMapping("/attendance/{user_identity}")
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
    @DeleteMapping("/attendance/{user_identity}")
    public ResponseFormat<Void> deleteAttendance(@PathVariable(name = "user_identity") String userIdentity,
                                                 @RequestParam(name = "date") LocalDate date) {

        userService.deleteAttendance(userIdentity, date);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                "출석 상태 정보가 성공적으로 삭제되었습니다"
        );
    }
}