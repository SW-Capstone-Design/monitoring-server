package kr.co.monitoringserver.controller.api;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.error.response.ResponseFormat;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
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
    public ResponseFormat<Void> createSecurityArea(SecurityAreaReqDTO.CREATE create) {

        securityAreaService.createSecurityArea(create);

        return ResponseFormat.successMessage(
                ErrorCode.SUCCESS_CREATED,
                create.getName() + " 보안구역 정보가 성공적으로 생성되었습니다"
        );
    }
}
