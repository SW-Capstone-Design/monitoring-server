package kr.co.monitoringserver.service.service;



import kr.co.monitoringserver.dto.request.UserRequestDto;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.enums.RoleType;
import kr.co.monitoringserver.persistence.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void join(UserRequestDto userDto) {
        Users users = Users.builder()
                .identity(userDto.getIdentity())
                .password(encoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .department(userDto.getDepartment())
                .role_type(RoleType.USER1)
                .build();

        userRepository.save(users);
    }

    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : bindingResult.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    @Transactional
    public void update(UserRequestDto userDto) {
        Users persistance = userRepository.findByIdentity(userDto.getIdentity())
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패");
                });

            String rawPassword = userDto.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setName(userDto.getName());
            persistance.setDepartment(userDto.getDepartment());
            persistance.setPhone(userDto.getPhone());
    }
}
