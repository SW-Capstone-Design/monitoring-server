package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeaconRepository extends JpaRepository<Beacon, Long> {
}
