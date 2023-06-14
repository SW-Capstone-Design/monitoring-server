package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.securityArea.UserSecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserSecurityAreaRepository extends JpaRepository<UserSecurityArea, Long> {

    Page<UserSecurityArea> findByUserAndSecurityArea(User user, SecurityArea securityArea, Pageable pageable);

    Long countByCreatedAt(LocalDate day);
}
