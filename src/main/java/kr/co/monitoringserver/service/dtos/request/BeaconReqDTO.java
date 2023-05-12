package kr.co.monitoringserver.service.dtos.request;

import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.service.enums.BeaconRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

}
