package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.entity.User;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.model.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(UserRequest userRequest) {
        final User user = User.of(userRequest);
        userRepository.save(user);
    }

}
