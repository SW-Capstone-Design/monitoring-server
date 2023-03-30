package kr.co.monitoringserver.service.service;


import jakarta.transaction.Transactional;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.model.RoleType;
import kr.co.monitoringserver.service.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void join(Users users) {
        String rawPassword = users.getPassword();
        String encPassword = encoder.encode(rawPassword);
        users.setPassword(encPassword);
        users.setRole_type(RoleType.USER1);
        userRepository.save(users);
    }

}
