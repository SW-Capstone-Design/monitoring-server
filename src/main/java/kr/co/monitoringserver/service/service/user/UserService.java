package kr.co.monitoringserver.service.service.user;

import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.user.UserReqDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.enums.UserRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    public final UserRepository userRepository;

    private final UserAttendanceRepository userAttendanceRepository;

    private final BCryptPasswordEncoder encoder;

    private final UserLocationService userLocationService;

    /**
     * join : 회원가입한다.
     */
    @Transactional
    public void join(UserReqDTO userDto) {

        User user = User.builder()
                .identity(userDto.getIdentity())
                .password(encoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .telephone(userDto.getTelephone())
                .department(userDto.getDepartment())
                .userRoleType(UserRoleType.USER1)
                .location(Location.builder()
                        .x(.0)
                        .y(.0)
                        .build())
                .build();

        userRepository.save(user);
    }

    /**
     * validateHandling : Validation을 구현한다.
     */
    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : bindingResult.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    /**
     * update : 회원정보를 수정한다.
     */
    @Transactional
    public void update(UserReqDTO userDto) {
        User persistance = userRepository.findByIdentity(userDto.getIdentity())
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패");
                });

        String rawPassword = userDto.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setName(userDto.getName());
        persistance.setDepartment(userDto.getDepartment());
        persistance.setTelephone(userDto.getTelephone());
    }

   /* ========================================================================== */

    /**
     * attendList : 출결정보를 조회한다.
     * Page 객체를 반환하기 위해 구현하였음.
     */
    public Page<UserAttendance> userAttendList(String identity, Pageable pageable) {

        return userAttendanceRepository.findByUser_Identity(identity, pageable);
    }

    /**
     * searchAttendList : 날짜를 지정하여 출결정보를 조회한다.
     * Page 객체를 반환하기 위해 구현하였음.
     */
    public Page<UserAttendance> searchUserAttendList(LocalDate searchKeyword, Pageable pageable) {

        return userAttendanceRepository.findByAttendance_Date(searchKeyword, pageable);
    }

    /**
     * tardinessAttendList : 지각한 회원을 조회합니다.
     */
    public Page<UserAttendance> tardinessAttendList(AttendanceType goWorkType, LocalDate date, Pageable pageable){

        return userAttendanceRepository.findByAttendance_GoWorkAndAttendance_Date(goWorkType, date.now(), pageable);
    }

    /**
     * searchTardinessAttendList : 날짜를 기준으로 지각한 회원을 조회합니다.
     */
    public Page<UserAttendance> searchTardinessAttendList(AttendanceType goWorkType, Pageable pageable, LocalDate searchKeyword) {

        return userAttendanceRepository.findByAttendance_GoWorkAndAttendance_Date(goWorkType, searchKeyword, pageable);
    }

    /**
     * earlyLeaveAttendList : 조퇴한 회원을 조회합니다.
     */
    public Page<UserAttendance> earlyLeaveAttendList(AttendanceType goWorkType, LocalDate date, Pageable pageable){

        return userAttendanceRepository.findByAttendance_LeaveWorkAndAttendance_Date(goWorkType, date.now(), pageable);
    }

    /**
     * searchEarlyLeaveAttendList : 날짜를 기준으로 조퇴한 회원을 조회합니다.
     */
    public Page<UserAttendance> searchEarlyLeaveAttendList(AttendanceType goWorkType, Pageable pageable, LocalDate searchKeyword) {

        return userAttendanceRepository.findByAttendance_LeaveWorkAndAttendance_Date(goWorkType, searchKeyword, pageable);
    }

    /**
     * Periodically Update All Users Locations Service
     * 모든 사용자의 위치를 주기적으로 업데이트
     */
    @Transactional
    public void updateAndSaveUserLocation(String userIdentity) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_USER));

        Location location = userLocationService.updateUserLocationByTrilateration(user);

        user.updateUserLocation(location);
    }
}