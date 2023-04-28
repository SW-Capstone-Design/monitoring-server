package kr.co.monitoringserver.controller.api;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaResDTO;
import kr.co.monitoringserver.service.service.SecurityAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/security_area")
public class SecurityAreaApiController {

    private final SecurityAreaService securityAreaService;

    /**
     * Create Security Area Controller
     */
    @PostMapping
    public ResponseFormat<Void> createSecurityArea(@RequestBody SecurityAreaReqDTO.CREATE create) {

        securityAreaService.createSecurityArea(create);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_CREATED,
                create.getName() + " 보안구역 정보가 성공적으로 생성되었습니다"
        );
    }

    /**
     * Get Security Area By id Controller
     */
    @GetMapping("/{security_area_id}")
    public ResponseFormat<SecurityAreaResDTO.READ> getSecurityAreaById(@PathVariable(name = "security_area_id") Long securityAreaId) {

        return ResponseFormat.successData(
                ErrorCode.SUCCESS_EXECUTE,
                securityAreaService.getSecurityAreaById(securityAreaId)
        );
    }

    /**
     * Update Security Area Controller
     */
    @PutMapping("/{security_area_id}")
    public ResponseFormat<Void> updateSecurityArea(@PathVariable(name = "security_area_id") Long securityAreaId,
                                                   @RequestBody SecurityAreaReqDTO.UPDATE update) {

        securityAreaService.updateSecurityArea(securityAreaId, update);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                update.getName() + " 보안구역 정보가 성공적으로 수정되었습니다"
        );
    }

    /**
     * Delete Security Area Controller
     */
    @DeleteMapping("/{security_area_id}")
    public ResponseFormat<Void> deleteSecurityArea(@PathVariable(name = "security_area_id") Long securityAreaId) {

        securityAreaService.deleteSecurityArea(securityAreaId);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_EXECUTE,
                "보안구역 정보가 성공적으로 삭제되었습니다"
        );
    }
}
