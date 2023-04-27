package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "beacon")
public class Beacon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beacon_id")
    private Long beaconId;

    @Column(name = "beacon_uuid")
    private Long beaconUuid;

    @Column(name = "beacon_name", length = 30)
    private String beaconName;

    @Column(name = "beacon_type")
    private String beaconType;

    @Column(name = "beacon_status")
    private String beaconStatus;
}
