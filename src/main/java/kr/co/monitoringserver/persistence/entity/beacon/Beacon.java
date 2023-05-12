package kr.co.monitoringserver.persistence.entity.beacon;

import jakarta.persistence.*;
import kr.co.monitoringserver.service.enums.BeaconRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_beacon")
public class Beacon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beacon_id")
    private Long beaconId;

    @Column(name = "beacon_uuid")
    private String uuid;

    @Column(name = "beacon_name")
    private String beaconName;

    @Column(name = "beacon_major")
    private Integer major;

    @Column(name = "beacon_minor", length = 30)
    private Integer minor;

    @Column
    private Short battery;

    @Enumerated(EnumType.STRING)
    @Column(name = "beacon_role")
    private BeaconRoleType beaconRole;

    @OneToMany(mappedBy = "beacon",
               cascade = CascadeType.REMOVE)
    private List<UserBeacon> Beacons = new ArrayList<>();
}
