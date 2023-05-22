package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.service.enums.BeaconRoleType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeaconResDTO {
    private Long beaconId;
    private String uuid;
    private Integer major;
    private Integer minor;

    private BeaconRoleType beaconRole;


    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ{

        private String beaconName;

        private Integer major;

        private Integer minor;

        private BeaconRoleType beaconRole;

        private Short battery;

        private String uuid;

        private Location location;
    }
}
