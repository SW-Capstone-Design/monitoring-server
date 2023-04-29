package kr.co.monitoringserver.service.dtos.request;

import jakarta.validation.constraints.NotBlank;
import kr.co.monitoringserver.persistence.entity.securityArea.Position;
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

        private Position location;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        private String name;

        private String description;

        private Position location;
    }
}
