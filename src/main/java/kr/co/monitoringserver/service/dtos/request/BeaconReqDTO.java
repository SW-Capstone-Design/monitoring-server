package kr.co.monitoringserver.service.dtos.request;

import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.service.enums.BeaconRoleType;
import lombok.*;


public class BeaconReqDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SERVER {
        private Long beaconId;

        private String uuid;

        private String beaconName;

        private Integer major;

        private Integer minor;

        private BeaconRoleType beaconRole;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CLIENT {
        private Beacon beacon;

        private Short rssi;

        private Short battery;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE{

        private String beaconName;

        private Integer major;

        private Integer minor;

        private BeaconRoleType beaconRole;

        private Short rssi;     // 사용자와 비콘 사이의 상대적 거리를 추정할 수 있도록, 각 비콘의 신호 강도 값을 전송

        private String uuid;    // 비콘 데이터를 관리하고 각 비콘이 어떤 것인지 식별할 수 있도록

        private Location locationX;

        private Location locationY;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE_LOCATION{

        private Short rssi;

        private String uuid;
    }
}
