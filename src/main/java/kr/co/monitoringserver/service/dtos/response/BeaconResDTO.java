package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.service.enums.BeaconRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
