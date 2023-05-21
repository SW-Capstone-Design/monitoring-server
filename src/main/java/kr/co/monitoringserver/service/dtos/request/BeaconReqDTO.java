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

        @NotBlank(message = "비콘 이름을 입력해주세요")
        private String beaconName;

        @NotNull(message = "비콘 그룹을 구분하기 위한 값을 입력해주세요")
        private Integer major;

        @NotNull(message = "비콘 그룹 내 개별 비콘을 구분하기 위한 값을 입력해주세요")
        private Integer minor;

        @NotBlank(message = "비콘 역할을 지정해주세요")
        private BeaconRoleType beaconRole;

        private Short rssi;

        @NotBlank(message = "비콘의 UUID 값을 입력해주세요")
        private String uuid;

        private Location location;  // 수동으로 입력할 위치 정보

        @NotBlank(message = "다른 비콘 간의 상대 거리를 입력해주세요")
        private List<BeaconLocationReqDTO.CREATE_LOCATION> createLocations;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE{

        private String beaconName;

        private Integer major;

        private Integer minor;

        private BeaconRoleType beaconRole;

        private Short rssi;

        private String uuid;

        private Location location;

        private List<BeaconLocationReqDTO.CREATE_LOCATION> createLocations;
    }
}
