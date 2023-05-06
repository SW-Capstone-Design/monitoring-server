package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.BadRequestException;
import kr.co.monitoringserver.infra.global.exception.DuplicatedException;
import kr.co.monitoringserver.infra.global.exception.InvalidInputException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.request.UserRequestDto;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.enums.RoleType;
import kr.co.monitoringserver.service.mappers.UserAttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
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

    private final AttendanceService attendanceService;

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
     * Create UserAttendance Service
     */
    @Transactional
    public void createAttendance(String userIdentity, AttendanceReqDTO.CREATE create) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        isAttendanceAlreadyTakenOnDate(user, create.getDate());

        AttendanceType goWork = Optional.ofNullable(create.getEnterTime())
                .map(attendanceService::calculateGoWorkAttendanceType)
                .orElseThrow(InvalidInputException::new);

        AttendanceType leaveWork = Optional.ofNullable(create.getLeaveTime())
                .map(attendanceService::calculateLeaveWorkAttendanceType)
                .orElseThrow(InvalidInputException::new);

        UserAttendance userAttendance = userAttendanceMapper.toUserAttendanceEntity(user, create, goWork, leaveWork);

        userAttendanceRepository.save(userAttendance);
    }

    /**
     * Get UserAttendance By User Identity Service
     */
    public Page<AttendanceResDTO.READ> getAttendanceByUserId(String userIdentity, Pageable pageable) {

        final Page<UserAttendance> userAttendancePage = userAttendanceRepository.findByUser_Identity(userIdentity, pageable);

        return userAttendancePage.map(userAttendanceMapper::toUserAttendacneReadDto);
    }

    /**
     * Get Absentee UserAttendance By Date Service
     */
    public Page<AttendanceResDTO.READ> getAbsenteeByDate(LocalDate date, Pageable pageable) {

        final Page<UserAttendance> userAttendancePage = userAttendanceRepository.findByAttendance_Date(date, pageable);

        final List<AttendanceResDTO.READ> absenteeList = userAttendancePage
                .stream()
                .filter(attendanceService::isAbsent)
                .map(userAttendanceMapper::toAttendTypeReadDto)
                .distinct()
                .collect(Collectors.toList());

        return new PageImpl<>(absenteeList, pageable, userAttendancePage.getTotalElements());
    }

    /**
     * Update UserAttendance Service
     */
    @Transactional
    public void updateAttendance(String userIdentity, AttendanceReqDTO.UPDATE update) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        final UserAttendance userAttendance = userAttendanceRepository.findByUserAndAttendance_Date(user, update.getDate())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        AttendanceType goWork = Optional.ofNullable(update.getEnterTime())
                .map(attendanceService::calculateGoWorkAttendanceType)
                .orElse(null);

        AttendanceType leaveWork = Optional.ofNullable(update.getLeaveTime())
                .map(attendanceService::calculateLeaveWorkAttendanceType)
                .orElse(null);

        userAttendance.updateAttendance(update, goWork, leaveWork);
    }

    /**
     * Delete UserAttendance Service
     */
    @Transactional
    public void deleteAttendance(String userIdentity, LocalDate date) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        final UserAttendance userAttendance = userAttendanceRepository.findByUserAndAttendance_Date(user, date)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE));

        userAttendanceRepository.delete(userAttendance);
    }

    private void isAttendanceAlreadyTakenOnDate(User user, LocalDate date) {

        UserAttendance userAttendance = userAttendanceRepository.findByUserAndAttendance_Date(user, date)
                .orElse(null);

        if (userAttendance != null) {
            throw new DuplicatedException(ErrorCode.DUPLICATE_USER_ATTENDANCE);
        }
    }
}