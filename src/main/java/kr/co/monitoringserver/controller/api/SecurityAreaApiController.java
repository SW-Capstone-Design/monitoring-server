package kr.co.monitoringserver.controller.api;

import kr.co.monitoringserver.infra.global.model.ResponseFormat;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.service.dtos.request.securityArea.SecurityAreaLocationReqDTO;
import kr.co.monitoringserver.service.dtos.request.securityArea.SecurityAreaReqDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaLocationResDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaResDTO;
import kr.co.monitoringserver.service.service.securityArea.SecurityAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/security_area")
public class SecurityAreaApiController {

    private final SecurityAreaService securityAreaService;

    /**
     * Create Security Area Controller
     */
    @PostMapping()
    public ResponseFormat<Void> createSecurityArea(@RequestBody @Validated SecurityAreaReqDTO.CREATE create,
                                                   Principal principal) {

        securityAreaService.createSecurityArea(principal, create);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_CREATED,
                create.getName() + "의 보안구역 정보가 성공적으로 생성되었습니다"
        );
    }

    /**
     * Get Security Area By id Controller
     */
    @GetMapping("/{security_area_id}")
    public ResponseFormat<Page<SecurityAreaResDTO.READ>> getSecurityAreaById(@PathVariable(name = "security_area_id") Long securityAreaId,
                                                                             @PageableDefault Pageable pageable,
                                                                             Principal principal) {

        return ResponseFormat.successData(
                ResponseStatus.SUCCESS_EXECUTE,
                securityAreaService.getSecurityAreaById(principal, securityAreaId, pageable)
        );
    }

    /**
     * Update Security Area Controller
     */
    @PutMapping("/{security_area_id}/{user_identity}")
    public ResponseFormat<Void> updateSecurityArea(@PathVariable(name = "security_area_id") Long securityAreaId,
                                                   @RequestBody @Validated SecurityAreaReqDTO.UPDATE update,
                                                   @PathVariable(name = "user_identity") String userIdentity) {

        securityAreaService.updateSecurityArea(userIdentity, securityAreaId, update);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_EXECUTE,
                update.getName() + "의 보안구역 정보가 성공적으로 수정되었습니다"
        );
    }

    /**
     * Delete Security Area Controller
     */
    @DeleteMapping("/{security_area_id}")
    public ResponseFormat<Void> deleteSecurityArea(@PathVariable(name = "security_area_id") Long securityAreaId,
                                                   Principal principal) {

        securityAreaService.deleteSecurityArea(principal, securityAreaId);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_EXECUTE,
                "보안구역 정보가 성공적으로 삭제되었습니다"
        );
    }



    /**
     * Handle User Access To SecurityArea Controller
     */
    @PostMapping("/access_log")
    public ResponseFormat<Void> handleUserAccessToSecurityArea(@RequestBody @Validated SecurityAreaLocationReqDTO.CREATE create) {

        boolean isAuthorized = securityAreaService.handleUserAccessToSecurityArea(create);

        String message = isAuthorized ?
                create.getUserIdentity() + "님의 보안구역 접근이 감지되었습니다. 인가된 사용자입니다." :
                create.getUserIdentity() + "님의 보안구역 접근이 감지되었습니다. 비인가된 사용자입니다.";

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_EXECUTE,
                message
        );
    }

    /**
     * Get User Security Area Access Logs Controller
     */
    @GetMapping("/access_log/{security_area_id}")
    public ResponseFormat<Page<SecurityAreaLocationResDTO.READ>> getUserSecurityAreaAccessLogs(@PathVariable(name = "security_area_id") Long securityAreaId,
                                                                                               @PageableDefault Pageable pageable,
                                                                                               Principal principal) {

        return ResponseFormat.successData(
                ResponseStatus.SUCCESS_EXECUTE,
                securityAreaService.getUserSecurityAreaAccessLogs(principal, securityAreaId, pageable)
        );
    }
}
