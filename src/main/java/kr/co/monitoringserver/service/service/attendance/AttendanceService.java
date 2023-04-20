package kr.co.monitoringserver.service.service.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.exception.UnauthorizedException;
import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.User;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.enums.RoleType;
import kr.co.monitoringserver.service.mappers.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    /**
     * 출석 서비스
     * 출석 기록과 출석 상태 간의 관계를 설정하고, 이를 활용하여 출석 상태를 출력하는 기능
     */

    private final UserRepository userRepository;

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    /** Create Attendance Service
     *
     */
    @Transactional
    public void createAttendance(AttendanceReqDTO.CREATE create) {

        Attendance attendance = attendanceMapper.toAttendacneEntity(create);

        attendanceRepository.save(attendance);
    }

    /** Get Attendance By userId Service
     *
     */
    public List<AttendanceResDTO.READ> getAttendanceByUserId(Long userId) {

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        return attendanceRepository.findByUser(user)
                .stream()
                .map(attendanceMapper::toAttendacneReadDto)
                .collect(Collectors.toList());
    }

    /** Update Attendance Service
     *
     */
    @Transactional
    public void updateAttendance(AttendanceReqDTO.UPDATE update) {

        final Attendance attendance = attendanceRepository.findById(update.getAttendanceId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ATTENDANCE));

        final User user = userRepository.findById(update.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        if (user.getRoleType() != RoleType.ADMIN) {
            throw new UnauthorizedException(ErrorCode.NOT_AUTHENTICATE_USER);
        }

        attendance.updateAttendance(update);

        attendanceRepository.save(attendance);
    }
}
