package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.BadRequestException;
import kr.co.monitoringserver.infra.global.exception.InvalidInputException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.request.UserReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.enums.RoleType;
import kr.co.monitoringserver.service.mappers.UserAttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    public final UserRepository userRepository;

    private final UserAttendanceRepository userAttendanceRepository;

    private final BCryptPasswordEncoder encoder;

    private final UserAttendanceMapper userAttendanceMapper;

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
                .roleType(RoleType.USER1)
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





    /**
     * Create UserAttendance Service
     */
    @Transactional
    public void createAttendance(String userIdentity, AttendanceReqDTO.CREATE create) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        AttendanceType goWork = Optional.ofNullable(create.getEnterTime())
                .map(this::calculateGoWorkAttendanceType)
                .orElseThrow(InvalidInputException::new);

        AttendanceType leaveWork = Optional.ofNullable(create.getLeaveTime())
                .map(this::calculateLeaveWorkAttendanceType)
                .orElseThrow(InvalidInputException::new);

        UserAttendance userAttendance = userAttendanceMapper.toUserAttendanceEntity(user, create, goWork, leaveWork);

        userAttendanceRepository.save(userAttendance);
    }




    /**
     * Get UserAttendance By User Identity Service
     */
    public List<AttendanceResDTO.READ> getAttendanceByUserId(Long userId) {

        List<UserAttendance> userAttendances = userAttendanceRepository.findListByUser_UserId(userId);

        Map<AttendanceType, Integer> attendanceDays = calculateAttendanceDays(userAttendances);

        userAttendanceMapper.toUserAttendanceReadDtoList(userAttendances, attendanceDays);

        return userAttendances
                .stream()
                .map(userAttendance -> userAttendanceMapper.toUserAttendacneReadDto(userAttendance, attendanceDays))
                .collect(Collectors.toList());
    }

    /**
     * attendList : 출결정보를 조회한다.
     * Page 객체를 반환하기 위해 구현하였음.
     */
    public Page<UserAttendance> userAttendList(Long userId, Pageable pageable) {

        return userAttendanceRepository.findByUser_UserId(userId, pageable);
    }

    /**
     * searchAttendList : 날짜를 지정하여 출결정보를 조회한다.
     * Page 객체를 반환하기 위해 구현하였음.
     */
    public Page<UserAttendance> searchUserAttendList(LocalDate searchKeyword, Pageable pageable) {

        return userAttendanceRepository.findByAttendance_Date(searchKeyword, pageable);
    }


    /**
     * Get Latecomer UserAttendance By Date Service
     */
    public List<AttendanceResDTO.READ> getLatecomerByDate(LocalDate date) {

        final List<UserAttendance> userAttendances = userAttendanceRepository.findByAttendance_Date(date);

        return userAttendances
                .stream()
                .filter(this::isLate)
                .map(userAttendanceMapper::toAttendTypeReadDto)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Get Absentee UserAttendance By userId Service
     */
    public List<AttendanceResDTO.READ> getAbsenteeByDate(LocalDate date) {

        final List<UserAttendance> userAttendances = userAttendanceRepository.findByAttendance_Date(date);

        return userAttendances
                .stream()
                .filter(this::isAbsent)
                .map(userAttendanceMapper::toAttendTypeReadDto)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Update UserAttendance Service
     */
    @Transactional
    public void updateAttendance(String userIdentity, AttendanceReqDTO.UPDATE update) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        final UserAttendance userAttendance = userAttendanceRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        AttendanceType goWork = Optional.ofNullable(update.getEnterTime())
                .map(this::calculateGoWorkAttendanceType)
                .orElse(null);

        AttendanceType leaveWork = Optional.ofNullable(update.getLeaveTime())
                .map(this::calculateLeaveWorkAttendanceType)
                .orElse(null);

        userAttendance.updateAttendance(update, goWork, leaveWork);
    }

    /**
     * Delete UserAttendance Service
     */
    @Transactional
    public void deleteAttendance(Long userId) {

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        final UserAttendance userAttendance = userAttendanceRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE));

        userAttendanceRepository.delete(userAttendance);
    }

    /**
     * 출근 시간 출석 상태 계산
     */
    private AttendanceType calculateGoWorkAttendanceType(LocalTime enterTime) {

        LocalTime startTime = LocalTime.parse("08:00:00");

        if (enterTime == null) {
            return AttendanceType.ABSENT;
        } else if (enterTime.isBefore(startTime)) {
            return AttendanceType.GO_WORK;
        } else {
            return AttendanceType.TARDINESS;
        }
    }

    /**
     * 퇴근 시간 출석 상태 계산
     */
    private AttendanceType calculateLeaveWorkAttendanceType(LocalTime leaveTime) {

        LocalTime endTime = LocalTime.parse("17:00:00");

        if (leaveTime == null) {
            return AttendanceType.ABSENT;
        } else if (leaveTime.isAfter(endTime)) {
            return AttendanceType.LEAVE_WORK;
        } else {
            return AttendanceType.EARLY_LEAVE;
        }
    }

    private Map<AttendanceType, Integer> calculateAttendanceDays(List<UserAttendance> userAttendances) {

        // TODO : 정상 출/퇴근이 이루어질 경우 ATTENDANCE 의 값을 증가

        Map<AttendanceType, Integer> attendanceDays = new HashMap<>();

        for (AttendanceType type : AttendanceType.values()) {
            attendanceDays.put(type, 0);
        }

        for (UserAttendance userAttendance : userAttendances) {
            AttendanceType goWorkType = userAttendance.getAttendance().getGoWork();
            if (goWorkType != null) {
                attendanceDays.put(goWorkType, attendanceDays.get(goWorkType) + 1);
            }
        }

        for (UserAttendance userAttendance : userAttendances) {
            AttendanceType leaveWorkType = userAttendance.getAttendance().getLeaveWork();
            if (leaveWorkType != null) {
                attendanceDays.put(leaveWorkType, attendanceDays.get(leaveWorkType) + 1);
            }
        }

        return attendanceDays;
    }

    /**
     * 사용자의 출석 상태가 지각인지 검사
     */
    private boolean isLate(UserAttendance userAttendance) {

        return userAttendance.getAttendance().getGoWork() == AttendanceType.TARDINESS;
    }

    /**
     * 사용자의 출석 상태가 결근인지 검사
     */
    private boolean isAbsent(UserAttendance userAttendance) {

        return userAttendance.getAttendance().getGoWork() == AttendanceType.ABSENT
                || userAttendance.getAttendance().getLeaveWork() == AttendanceType.ABSENT;
    }
}