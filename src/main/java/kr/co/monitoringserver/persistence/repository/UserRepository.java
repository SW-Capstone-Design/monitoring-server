package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.service.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByIdentity(String username);

    Optional<User> findByIdentity(String identity);

    Optional<User> findByUserId(Long userId);

    Page<User> findByIdentityContaining(String searchKeyword, Pageable pageable);
}
