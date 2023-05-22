package kr.co.monitoringserver.service.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class SecurityAreaReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        @NotBlank(message = "보안구역의 이름을 입력해주세요")
        private String name;

        private String description;

//        private Location location;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        private String name;

        private String description;

//        private Location location;
    }
}
