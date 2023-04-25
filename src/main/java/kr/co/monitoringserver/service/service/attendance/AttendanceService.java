package kr.co.monitoringserver.service.service.attendance;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.persistence.entity.User;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.mappers.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    // TODO : 출근, 퇴근 등의 기록을 바탕으로 특정 사용자의 근무 시간을 계산
    // TODO : 근무 시간을 기준으로 근무 일수나 근로 대장을 작성하는 기능

    private final UserRepository userRepository;

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    /**
     * Get User Attendance Records Service
     * 특정 사용자의 출석 기록을 조회
     */
    public List<AttendanceResDTO.READ> getAttendanceRecordsByUserId(Long userId) {

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));

        final List<AttendanceStatus> attendanceStatuses = attendanceRepository.findByUser(user);

        return attendanceStatuses
                .stream()
                .map(attendanceMapper::toAttendacneReadDto)
                .collect(Collectors.toList());
    }


    /**
     * Get User Attendance Records By Date Service
     * 특정 일자의 모든 사용자의 출석 기록을 조회
     * 해당 날짜의 요일까지 파악 - 만약 attendanceStatus 가 empty List 일 경우 공휴일/주말임을 명시
     */
    public List<AttendanceResDTO.READ> getAllUserAttendanceRecordsByDate(LocalDate date) {

        final List<AttendanceStatus> attendanceStatuses = attendanceRepository.findByAttendanceStatusDate(date);

        return attendanceStatuses
                .stream()
                .map(attendanceMapper::toAttendacneReadDto)
                .collect(Collectors.toList());
    }


    /**
     * Get User Attendance Records By Specific Period Service
     * 특정 기간 동안의 모든 사용자의 출석 기록을 조회
     */
    public List<AttendanceResDTO.READ> getAllUserAttendanceRecordsByPeriod(LocalDate startDate, LocalDate endDate) {

        List<AttendanceStatus> attendanceStatuses =
                attendanceRepository.findAllByAttendanceStatusBetween(startDate, endDate);

        return attendanceStatuses
                .stream()
                .map(attendanceMapper::toAttendacneReadDto)
                .collect(Collectors.toList());
    }
}
