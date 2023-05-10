package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityAreaRepository extends JpaRepository<SecurityArea, Long> {

    Page<SecurityArea> findById(Long securityAreaId, Pageable pageable);
}
