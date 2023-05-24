package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import kr.co.monitoringserver.persistence.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBeaconRepository extends JpaRepository<UserBeacon, Long> {
//    UserBeacon findByBeacon_BeaconId(Long beaconId);

    Optional<UserBeacon> findByUserBeaconId(Long userBeaconId);

    Optional<UserBeacon> findByBeacon_BeaconId(Long userBeaconId);

    List<UserBeacon> findByUser_UserId(Long userId);

    Optional<UserBeacon> findByUserAndBeacon(User user, Beacon beacon);
}
