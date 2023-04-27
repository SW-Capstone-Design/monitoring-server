package kr.co.monitoringserver.service.service.user;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.BadRequestException;
import kr.co.monitoringserver.infra.global.exception.InvalidInputException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.UserAttendance;
import kr.co.monitoringserver.persistence.entity.User;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
import kr.co.monitoringserver.service.dtos.request.UserAttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.request.UserRequestDto;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.response.UserAttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.enums.RoleType;
import kr.co.monitoringserver.service.mappers.UserAttendanceMapper;
import lombok.RequiredArgsConstructor;
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

    private final AttendanceRepository attendanceRepository;

    private final BCryptPasswordEncoder encoder;

    private final UserAttendanceMapper userAttendanceMapper;

    @Transactional
    public void join(UserRequestDto userDto) {
        User user = User.builder()
                .identity(userDto.getIdentity())
                .password(encoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .telephone(userDto.getPhone())
                .department(userDto.getDepartment())
                .roleType(RoleType.USER1)
                .build();

        userRepository.save(user);
    }

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
        User persistance = userRepository.findByIdentity(userDto.getIdentity())
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패");
                });

            String rawPassword = userDto.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setName(userDto.getName());
            persistance.setDepartment(userDto.getDepartment());
            persistance.setTelephone(userDto.getPhone());
    }



    /**
     * Create UserAttendance Status Service
     */
    @Transactional
    public void createAttendance(String userIdentity, UserAttendanceReqDTO.CREATE create) {

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
     * Get UserAttendance Status By userId Service
     */
    public List<UserAttendanceResDTO.READ> getAttendanceByUserId(Long userId) {

        List<UserAttendance> userAttendances = userAttendanceRepository.findByUser_UserId(userId);

        Map<AttendanceType, Integer> attendanceDays = calculateAttendanceDays(userAttendances);

        userAttendanceMapper.toUserAttendanceReadDtoList(userAttendances, attendanceDays);

        return userAttendances
                .stream()
                .map(userAttendance -> userAttendanceMapper.toUserAttendacneReadDto(userAttendance, attendanceDays))
                .collect(Collectors.toList());
    }

    /**
     * Get Tardiness User UserAttendance Status By Date Service
     */
    public List<UserAttendanceResDTO.READ> getTardinessUserByDate(LocalDate date) {

        final List<UserAttendance> userAttendances = userAttendanceRepository.findByAttendance_Date(date);

        return userAttendances
                .stream()
                .filter(this::isLate)
                .map(userAttendanceMapper::toAttendTypeReadDto)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Get Absent User UserAttendance Status By userId Service
     */
    public List<UserAttendanceResDTO.READ> getAbsentUserByDate(LocalDate date) {

        final List<UserAttendance> userAttendances = userAttendanceRepository.findByAttendance_Date(date);

        return userAttendances
                .stream()
                .filter(this::isAbsent)
                .map(userAttendanceMapper::toAttendTypeReadDto)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Update UserAttendance Status Service
     */
    @Transactional
    public void updateAttendance(String userIdentity, UserAttendanceReqDTO.UPDATE update) {

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
     * Delete UserAttendance Status By id Service
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
     * 출석 상태 판별 기능
     * 출근 : 08:00:00 이전 출근할 경우
     * 지각 : 08:00:00 이후 or 17:00:00 이전 출근할 경우
     * 조퇴 : 정상 출근 후 17:00:00 이전 퇴근할 경우
     * 결근 : 08:00:00 ~ 17:00:00 사이 어떠한 출/퇴근도 없을 경우
     * 퇴근 : 17:00:00 이후 퇴근할 경우
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

    private boolean isLate(UserAttendance userAttendance) {

        return userAttendance.getAttendance().getGoWork() == AttendanceType.TARDINESS;
    }

    private boolean isAbsent(UserAttendance userAttendance) {

        return userAttendance.getAttendance().getGoWork() == AttendanceType.ABSENT
                || userAttendance.getAttendance().getLeaveWork() == AttendanceType.ABSENT;
    }
}
