package kr.co.monitoringserver.service.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class SecurityAreaReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        @NotBlank(message = "Please enter your security area name")
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
