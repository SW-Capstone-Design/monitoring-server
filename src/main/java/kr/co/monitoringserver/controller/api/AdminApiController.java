package kr.co.monitoringserver.controller.api;

import jakarta.validation.Valid;
import kr.co.monitoringserver.service.dtos.request.fcm.FCMRequestDTO;
import kr.co.monitoringserver.service.dtos.request.user.AdminReqDTO;
import kr.co.monitoringserver.service.dtos.request.user.UserReqDTO;
import kr.co.monitoringserver.service.dtos.response.ResponseDto;
import kr.co.monitoringserver.service.service.user.AdminService;
import kr.co.monitoringserver.service.service.fcm.FirebaseCloudMessageService;
import kr.co.monitoringserver.service.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;

    private final UserService userService;

    public static List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    private final FirebaseCloudMessageService firebaseCloudMessageService;


    /**
     * saveUser : 사용자정보를 Create하여 회원가입을 수행한다.
     */
    @PostMapping("/admin/joinProc")
    public ResponseDto<?> saveUser(@Valid @RequestBody UserReqDTO userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(bindingResult);

            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        userService.join(userDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * updateUser : 회원정보를 Update 한다.
     */
    @PutMapping("/admin")
    public ResponseDto<?> updateUser(@Valid @RequestBody AdminReqDTO adminDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            Map<String, String> validatorResult = adminService.validateHandling(bindingResult);

            return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), validatorResult);
        }

        adminService.update(adminDto);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * deleteUser : 회원정보를 Delete 한다.
     */
    @DeleteMapping("/admin/info/delete")
    public void deleteUser(@RequestBody UserReqDTO userReqDTO){

        adminService.deleteUser(userReqDTO);

    }

    @DeleteMapping("/admin/alert/delete")
    public void deleteAlert(){

        adminService.deleteAlert();
    }

    @DeleteMapping("/admin/alert/delete/ten")
    public void deleteAlertTopTen(){

        adminService.deleteAlertTopTen();
    }

    @DeleteMapping("/admin/alert/delete/all")
    public void deleteAlertAll(){

        adminService.deleteAlertAll();
    }

    @RequestMapping(value = "/auth/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        emitters.add(sseEmitter);
        sseEmitter.onCompletion(()->emitters.remove(sseEmitter));
        sseEmitter.onTimeout(() -> {
            sseEmitter.complete();
        });

        return sseEmitter;
    }

    @PostMapping("/auth/fcm")
    public ResponseEntity pushMessage(@RequestBody FCMRequestDTO fCMRequestDTO) throws IOException {
        System.out.println(fCMRequestDTO.getTargetToken() + " "
                + fCMRequestDTO.getTitle() + " " + fCMRequestDTO.getBody());

        firebaseCloudMessageService.sendMessageTo(
                fCMRequestDTO.getTargetToken(),
                fCMRequestDTO.getTitle(),
                fCMRequestDTO.getBody());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/setFCMToken")
    public void setFCMToken(@RequestBody AdminReqDTO adminReqDTO, Principal principal){

        adminService.setFCMToken(adminReqDTO, principal);
    }

}