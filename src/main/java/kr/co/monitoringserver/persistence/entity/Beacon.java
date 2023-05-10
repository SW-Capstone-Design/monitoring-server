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

    @Column(name = "beacon_uuid", unique = true)
    private String uuid;

    @Column(name = "beacon_name")
    private String beaconName;

    @Column(name = "beacon_major")
    private Integer major;

    @Column(name = "beacon_minor", length = 30)
    private Integer minor;
}
