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
    private String uuid;

    private String major;

    private String minor;

    private Long rssi;
}
