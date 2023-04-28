package kr.co.monitoringserver.service.service.attendance;

import kr.co.monitoringserver.persistence.repository.UserAttendanceRepository;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.mappers.UserAttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAttendanceService {

    /**
     * 출석 서비스
     * 출석 기록과 출석 상태 간의 관계를 설정하고, 이를 활용하여 출석 상태를 출력하는 기능
     */

    private final UserRepository userRepository;

    private final UserAttendanceRepository userAttendanceRepository;

    private final AttendanceRepository attendanceRepository;

    private final UserAttendanceMapper userAttendanceMapper;

//    /**
//     * Get User UserAttendance Records Service
//     * 특정 사용자의 출석 기록을 조회
//     */
//    public List<UserAttendanceResDTO.READ> getAttendanceRecordsByUserId(Long userId) {
//
//        final User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
//
//        Attendance attendanceStatus = attendanceRepository.findByUser(user)
//                .orElseThrow();
//
//        return attendanceStatus;
//    }
//
//    /**
//     * Get User UserAttendance Records By Date Service
//     * 특정 일자의 모든 사용자의 출석 기록을 조회
//     */
//    public List<UserAttendanceResDTO.READ> getAllUserAttendanceRecordsByDate(LocalDate date) {
//
//        final List<Attendance> attendanceStatuses = attendanceRepository.findByAttendanceStatusDate(date);
//
//        return attendanceStatuses
//                .stream()
//                .map(userAttendanceMapper::toAttendacneReadDto)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Get User UserAttendance Records By Specific Period Service
//     * 특정 기간 동안의 모든 사용자의 출석 기록을 조회
//     */
//    public List<UserAttendanceResDTO.READ> getAllUserAttendanceRecordsByPeriod(LocalDate startDate, LocalDate endDate) {
//
//        List<Attendance> attendanceStatuses =
//                attendanceRepository.findAllByAttendanceStatusBetween(startDate, endDate);
//
//        return attendanceStatuses
//                .stream()
//                .map(userAttendanceMapper::toAttendacneReadDto)
//                .collect(Collectors.toList());
//    }
}
