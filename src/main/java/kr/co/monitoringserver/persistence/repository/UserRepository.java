package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.persistence.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByIdentity(String username);

    Optional<Users> findByUsersId(long usersId);

    Page<Users> findByIdentityContaining(String searchKeyword, Pageable pageable);
}
