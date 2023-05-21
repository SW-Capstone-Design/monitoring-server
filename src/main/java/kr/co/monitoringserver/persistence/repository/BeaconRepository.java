package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BeaconRepository extends JpaRepository<Beacon, Long> {

    Optional<Beacon> findOptionalByBeaconId(Long beaconId);

//    Beacon findByBeaconId(Long beaconId);

    @Query(" select b " +
            "from Beacon b " +
            "where b.battery < :threshold")
    List<Beacon> findBeaconsByBatteryLessThan(int threshold);

    List<Long> getAllBeaconIds();

    Optional<UserBeacon> findByBeaconId(Long beaconId);
}
