package kr.co.monitoringserver.service.service.user;

import kr.co.monitoringserver.persistence.entity.alert.IndexNotification;
import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.IndexNotificationRepository;
import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.user.AdminReqDTO;
import kr.co.monitoringserver.service.dtos.request.user.UserReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    private final UserAttendanceRepository userAttendanceRepository;

    private final IndexNotificationRepository indexNotificationRepository;

    /**
     * list : 모든 회원정보를 조회한다.
     */
    public Page<User> list(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    /**
     * detail : 회원정보 수정을 위해 특정 회원을 Select 한다.
     */
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
        persistence.setUserRoleType(adminDto.getUserRoleType());
    }

    /**
     * deleteUser : 회원정보를 삭제하여 탈퇴한다.
     */
    @Transactional
    public void deleteUser(UserReqDTO userReqDTO){
        User user = userRepository.findByUserId(userReqDTO.getUserId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("유저 조회 실패 : 아이디를 찾을 수 없습니다.");
                });
        userRepository.delete(user);
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

    /**
     * attendDetail : 해당 userId, date 기반의 출결정보를 조회한다.
     */
    public UserAttendance attendDetail(Long userId,LocalDate date){

        return userAttendanceRepository.findByUser_UserIdAndAttendance_Date(userId, date);
    }

    /**
     * attendList : 출결정보를 조회한다.
     */
    public Page<UserAttendance> attendList(Pageable pageable) {

        return userAttendanceRepository.findAll(pageable);
    }

    /**
     * searchAttendList : 키워드를 통해 날짜를 지정하여 출결정보를 조회한다.
     */
    @Transactional(readOnly = true)
    public Page<UserAttendance> searchAttendList(LocalDate searchKeyword, Pageable pageable) {

        return userAttendanceRepository.findByAttendance_Date(searchKeyword, pageable);
    }

    /**
     * alertList : 관리자페이지에 새알림 목록을 가져온다.
     */
    public Page<IndexNotification> alertList(Pageable pageable){

        return indexNotificationRepository.findAll(pageable);
    }

    /**
     * deleteAlert : 관리자페이지의 최근 알림 1개를 삭제한다.
     */
    @Transactional
    public void deleteAlert(){

        IndexNotification indexNotification = indexNotificationRepository.findTop1ByOrderByIndexAlertTimeDesc()
                .orElseThrow(()->{
                    return new IllegalArgumentException("알림 조회 실패 : 알림을 찾을 수 없습니다.");
                });

        indexNotificationRepository.delete(indexNotification);
    }

    /**
     * deleteAlertTopTen : 관리자페이지의 최근 알림 10개를 삭제한다.
     */
    @Transactional
    public void deleteAlertTopTen(){

        List<IndexNotification> list = indexNotificationRepository.findTop10ByOrderByIndexAlertTimeDesc();

        indexNotificationRepository.deleteAll(list);
    }

    /**
     *  deleteAlertAll : 관리자페이지의 모든 알림을 삭제한다.
     */
    @Transactional
    public void deleteAlertAll() {

        indexNotificationRepository.deleteAll();
    }

    @Transactional
    public void setFCMToken(AdminReqDTO adminReqDTO, Principal principal){

        User user = userRepository.findByIdentity(principal.getName())
                .orElseThrow(()->{
                    return new IllegalArgumentException("유저 조회 실패 : 아이디를 찾을 수 없습니다.");
                });

        user.setDeviceToken(adminReqDTO.getToken());
    }
}