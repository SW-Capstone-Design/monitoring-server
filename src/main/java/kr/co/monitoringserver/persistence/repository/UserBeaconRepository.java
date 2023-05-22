package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBeaconRepository extends JpaRepository<UserBeacon, Long> {
    UserBeacon findByBeacon_BeaconId(Long beaconId);

    Optional<UserBeacon> findByUserBeaconId(Long userBeaconId);

    List<UserBeacon> findByUser_UserId(Long userId);
}
