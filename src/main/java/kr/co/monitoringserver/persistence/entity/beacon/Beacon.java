package kr.co.monitoringserver.persistence.entity.beacon;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.service.dtos.request.beacon.BeaconReqDTO;
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
    private String uuid;        // 비콘을 식별하기 위해 사용되며, 각 비콘은 서로 다른 UUID 값을 갖음

    @Column(name = "beacon_name")
    private String beaconName;  // 비콘의 이름

    @Column(name = "beacon_major")
    private Integer major;      // 비콘 그룹을 구분하는데 사용(같은 건물 내의 다른 층을 구분)

    @Column(name = "beacon_minor", length = 30)
    private Integer minor;      // 비콘 그룹 내 개별 비콘을 구분하는데 사용(같은 건물 내에서 다른 방에 있는 비콘을 구분)

    @Column(name = "beacon_battery")
    private Short battery;      // 비콘의 배터리 수준을 나타내는 값

    @Column(name = "beacon_tx_power", nullable = false)
    private int txPower;        // 비콘의 송신 전력(비콘에서 출력되는 신호의 강도)

    @Embedded
    @Column(name = "beacon_location")
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(name = "beacon_role")
    private BeaconRoleType beaconRole;

    @OneToMany(mappedBy = "beacon",
               cascade = CascadeType.REMOVE)
    private List<UserBeacon> Beacons = new ArrayList<>();



    public void createBeaconLocation(Location location) {

        this.location = location;
    }

    public void updateBeaconInfoAndLocation(BeaconReqDTO.UPDATE update) {

            this.beaconName = update.getBeaconName();
            this.uuid = update.getUuid();
            this.major = update.getMajor();
            this.minor = update.getMinor();
            this.beaconRole = update.getBeaconRole();
            this.location = update.getLocation();
            this.txPower = update.getTxPower();
    }
}
