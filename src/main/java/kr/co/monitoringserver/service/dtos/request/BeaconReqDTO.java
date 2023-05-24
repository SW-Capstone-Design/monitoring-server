package kr.co.monitoringserver.service.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.service.enums.BeaconRoleType;
import lombok.*;

import java.util.List;

public class BeaconReqDTO {

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

        @NotBlank(message = "비콘 이름을 입력해주세요")
        private String beaconName;

        @NotBlank(message = "비콘의 UUID 값을 입력해주세요")
        private String uuid;


        @NotNull(message = "비콘 그룹을 구분하기 위한 값을 입력해주세요")
        private Integer major;

        @NotNull(message = "비콘 그룹 내 개별 비콘을 구분하기 위한 값을 입력해주세요")
        private Integer minor;

        private Short battery;

        private BeaconRoleType beaconRole;

        private Location location;

        private List<BeaconLocationReqDTO.LOCATION> locationList;

        private Short rssi;     // -99 dBm ~ 35 dBm

        private String userIdentity;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE{

        private String beaconName;

        private String uuid;

        private Integer major;

        private Integer minor;

        private Short battery;

        private BeaconRoleType beaconRole;

        private Location location;

        private List<BeaconLocationReqDTO.LOCATION> locationList;

        private Short rssi;

        @NotBlank(message = "사용자의 아이디를 입력해주세요")
        private String userIdentity;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MAPPING {

        private String userIdentity;

        private Short rssi;
    }
}
