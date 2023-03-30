package kr.co.monitoringserver.persistence.repository;

import kr.co.monitoringserver.service.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByIdentity(String username);
}