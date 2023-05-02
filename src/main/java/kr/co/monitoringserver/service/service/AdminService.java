package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.service.dtos.request.AdminReqDTO;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * list : 모든 회원정보를 조회한다.
     */
    @Transactional(readOnly = true)
    public Page<User> list(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    /**
     * detail : 회원정보 수정을 위해 특정 회원을 Select 한다.
     */
    @Transactional(readOnly = true)
    public User detail(Long userId) {

        return userRepository.findByUserId(userId)
                .orElseThrow(()->{
                    return new IllegalArgumentException("유저 조회 실패 : 아이디를 찾을 수 없습니다.");
                });
    }

    /**
     * update : 회원정보를 수정한다.
     */
    @Transactional
    public void update(AdminReqDTO adminDto) {
        User persistence = userRepository.findByIdentity(adminDto.getIdentity())
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패");
                });

        String rawPassword = adminDto.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistence.setPassword(encPassword);
        persistence.setName(adminDto.getName());
        persistence.setDepartment(adminDto.getDepartment());
        persistence.setTelephone(adminDto.getTelephone());
        persistence.setRoleType(adminDto.getRoleType());
    }

    /**
     * validateHandling : Validation 적용을 위한 메소드
     */
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : bindingResult.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    /**
     * userSearchList : 회원정보를 조회한다.
     */
    public Page<User> userSearchList(String searchKeyword, Pageable pageable) {

        return userRepository.findByIdentityContaining(searchKeyword, pageable);
    }

}