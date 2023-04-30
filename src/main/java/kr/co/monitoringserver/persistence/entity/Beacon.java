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
    private long beaconId;

    @Column(name = "beacon_uuid")
    private String uuid;

    @Column(name = "beacon_major")
    private String major;

    @Column(name = "beacon_minor", length = 30)
    private String minor;

    @Column(name = "beacon_rssi")
    private Long rssi;
}
