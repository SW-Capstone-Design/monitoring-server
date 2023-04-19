package kr.co.monitoringserver.infra.config.auth;

import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User principal = userRepository.findByIdentity(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + username);
                });
        return new PrincipalDetail(principal);
    }

}
