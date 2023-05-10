package kr.co.monitoringserver.service.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeaconReqDTO {
    private Long beaconId;
    private String uuid;

    private String beaconName;

    private Integer major;

    private Integer minor;

}
