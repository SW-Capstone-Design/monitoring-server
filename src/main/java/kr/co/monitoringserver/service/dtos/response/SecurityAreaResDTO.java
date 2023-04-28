package kr.co.monitoringserver.service.dtos.response;

import lombok.*;

import javax.swing.text.Position;

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
