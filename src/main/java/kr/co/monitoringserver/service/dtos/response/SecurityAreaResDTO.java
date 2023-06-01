package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.persistence.entity.Location;
import lombok.*;

public class SecurityAreaResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private String name;

        private String description;

        private Location lowerLeft;

        private Location upperRight;
    }
}
