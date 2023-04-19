package kr.co.monitoringserver.service.service.attend;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.mappers.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    @Transactional
    public void createAttendance(AttendanceReqDTO.CREATE create) {

        Attendance attendance = attendanceMapper.toAttendacneEntity(create);

        attendanceRepository.save(attendance);
    }

    public AttendanceResDTO.READ getAttendanceById(Long attendanceId) {

        final Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Not Found Attendance"));

        return attendanceMapper.toAttendacneReadDto(attendance);
    }
}
