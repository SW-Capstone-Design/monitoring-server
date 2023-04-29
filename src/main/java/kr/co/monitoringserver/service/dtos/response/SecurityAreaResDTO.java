package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.persistence.entity.securityArea.Position;
import lombok.*;

public class SecurityAreaResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private String name;

        private String description;

        private Position location;
    }
}
