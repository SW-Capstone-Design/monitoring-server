package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.Beacon;
import kr.co.monitoringserver.service.dtos.response.BeaconResDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BeaconRepository extends JpaRepository<Beacon, Long> {

    Optional<Beacon> findOptionalByBeaconId(Long beaconId);
    Beacon findByBeaconId(Long beaconId);

}
