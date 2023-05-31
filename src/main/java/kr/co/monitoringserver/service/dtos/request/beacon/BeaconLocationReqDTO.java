package kr.co.monitoringserver.service.dtos.request.beacon;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public class BeaconLocationReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LOCATION {

        @NotNull(message = "비콘의 번호를 입력해주세요")
        private Long beaconId;

        private double distance;

        private double x;

        private double y;
    }
}
