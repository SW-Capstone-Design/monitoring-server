package kr.co.monitoringserver.persistence.entity.beacon;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_user_beacon")
public class UserBeacon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_beacon_id")
    private Long userBeaconId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beacon_id", nullable = false)
    private Beacon beacon;

    @Column(name = "beacon_rssi")
    private Short rssi;


    public void updateUserAndBeacon(User user,
                                    Beacon beacon,
                                    Short rssi) {

        this.user = user;
        this.beacon = beacon;
        this.rssi = rssi;
    }

    public void updateRssi(Short rssi) {

        this.rssi = rssi;
    }
}
