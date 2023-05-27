package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.exception.BadRequestException;
import kr.co.monitoringserver.infra.global.exception.DuplicatedException;
import kr.co.monitoringserver.infra.global.exception.InvalidInputException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.mappers.UserAttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final UserRepository userRepository;

    private final UserAttendanceRepository userAttendanceRepository;

    private final UserAttendanceMapper userAttendanceMapper;

    // TODO : 수정 - 퇴근 정보 수정, 수정 로직 실행 시 기존에 생성된 출석 정보의 값을 수정하는게 아닌 새로운 출석 정보를 생성함

    /**
     * Create And Save User Clock In Service
     */
    @Transactional
    public void createAndSaveUserClockIn(String userIdentity, AttendanceReqDTO.CREATE create) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        isAttendanceAlreadyTakenOnDate(user, create.getDate());

        AttendanceType goWork = Optional.ofNullable(create.getEnterTime())
                .map(this::calculateGoWorkAttendanceType)
                .orElseThrow(InvalidInputException::new);

        UserAttendance userAttendance = userAttendanceMapper.toUserAttendanceEntity(user, create, goWork);

        userAttendanceRepository.save(userAttendance);
    }

    /**
     * Update And Save User Clock Out Service
     */
    @Transactional
    public void updateAndSaveUserClockOut(String userIdentity, AttendanceReqDTO.UPDATE update) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        final UserAttendance userAttendance = userAttendanceRepository.findByUserAndAttendance_Date(user, update.getDate())
                        .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_ATTENDANCE));

        AttendanceType leaveWork = Optional.ofNullable(update.getLeaveTime())
                .map(this::calculateLeaveWorkAttendanceType)
                .orElseThrow(InvalidInputException::new);

        userAttendance.updateClockOutRecord(update, leaveWork);
    }

    /**
     * Get UserAttendance By User Identity Service
     */
    public Page<AttendanceResDTO.READ> getAttendanceByUserIdentity(String userIdentity, Pageable pageable) {

        final Page<UserAttendance> userAttendancePage =
                userAttendanceRepository.findByUser_Identity(userIdentity, pageable);

        return userAttendancePage.map(userAttendanceMapper::toUserAttendanceReadDto);
    }

    /**
     * Get Latecomer UserAttendance By Date Service
     */
    public Page<AttendanceResDTO.READ> getLatecomerByDate(LocalDate date, Pageable pageable) {

        return getAttendanceListByStatus(date, AttendanceType.TARDINESS, pageable);
    }

    /**
     * Get Absentee UserAttendance By Date Service
     */
    public Page<AttendanceResDTO.READ> getAbsenteeByDate(LocalDate date, Pageable pageable) {

        return getAttendanceListByStatus(date, AttendanceType.ABSENT, pageable);
    }

    /**
     * Update UserAttendance Service
     */
    @Transactional
    public void updateAttendance(String userIdentity, AttendanceReqDTO.UPDATE update) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        final UserAttendance userAttendance = userAttendanceRepository.findByUserAndAttendance_Date(user, update.getDate())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_ATTENDANCE));

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
    public void deleteAttendance(String userIdentity, LocalDate date) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_USER));

        final UserAttendance userAttendance = userAttendanceRepository.findByUserAndAttendance_Date(user, date)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_ATTENDANCE));

        userAttendanceRepository.delete(userAttendance);
    }



    // 출근 시간 출석 상태 계산
    public AttendanceType calculateGoWorkAttendanceType(LocalTime enterTime) {

        LocalTime startTime = LocalTime.parse("08:00:00");

        if (enterTime == null) {
            return AttendanceType.ABSENT;
        } else if (enterTime.isBefore(startTime)) {
            return AttendanceType.GO_WORK;
        } else {
            return AttendanceType.TARDINESS;
        }
    }

    // 퇴근 시간 출석 상태 계산
    public AttendanceType calculateLeaveWorkAttendanceType(LocalTime leaveTime) {

        LocalTime endTime = LocalTime.parse("17:00:00");

        if (leaveTime == null) {
            return AttendanceType.ABSENT;
        } else if (leaveTime.isAfter(endTime)) {
            return AttendanceType.LEAVE_WORK;
        } else {
            return AttendanceType.EARLY_LEAVE;
        }
    }

    // 사용자의 출석 상태가 지각인지 검사
    public boolean isLate(UserAttendance userAttendance) {

        return userAttendance.getAttendance().getGoWork() == AttendanceType.TARDINESS;
    }

    // 사용자의 출석 상태가 결근인지 검사
    public boolean isAbsent(UserAttendance userAttendance) {

        return userAttendance.getAttendance().getGoWork() == AttendanceType.ABSENT
                || userAttendance.getAttendance().getLeaveWork() == AttendanceType.ABSENT;
    }

    // 해당 날짜의 출석 여부를 검사
    private void isAttendanceAlreadyTakenOnDate(User user, LocalDate date) {

        UserAttendance userAttendance = userAttendanceRepository.findByUserAndAttendance_Date(user, date)
                .orElse(null);

        if (userAttendance != null) {
            throw new DuplicatedException();
        }
    }

    private Page<AttendanceResDTO.READ> getAttendanceListByStatus(LocalDate date, AttendanceType status, Pageable pageable) {

        final Page<UserAttendance> userAttendancePage = userAttendanceRepository.findByAttendance_Date(date, pageable);

        final List<AttendanceResDTO.READ> attendanceList = userAttendancePage
                .stream()
                .filter(userAttendance -> status == AttendanceType.TARDINESS ?
                        this.isLate(userAttendance) : this.isAbsent(userAttendance))
                .map(userAttendanceMapper::toAttendTypeReadDto)
                .distinct()
                .collect(Collectors.toList());

        return new PageImpl<>(attendanceList, pageable, userAttendancePage.getTotalElements());
    }

    private void validateUserClockInExists(UserAttendance userAttendance) {

        if (userAttendance.getAttendance().getGoWork() == null) {
            throw new NotFoundException(ResponseStatus.NOT_FOUND_ATTENDANCE);
        }
    }
}
