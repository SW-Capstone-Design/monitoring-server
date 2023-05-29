package kr.co.monitoringserver.service.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

public class SecurityAreaLocationReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        @NotNull(message = "보안구역 ID를 입력해주세요")
        private Long securityAreaId;

        @NotBlank(message = "사용자 ID를 입력해주세요")
        private String userIdentity;

        private LocalTime accessTime;
    }
}
