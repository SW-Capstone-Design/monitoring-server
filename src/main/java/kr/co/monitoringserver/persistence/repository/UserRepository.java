package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdentity(String username);

    Optional<User> findByUsersId(long usersId);

    Page<User> findByIdentityContaining(String searchKeyword, Pageable pageable);
}
