package kr.co.monitoringserver.service.service.attendance;

import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.persistence.repository.AttendanceRepository;
import kr.co.monitoringserver.persistence.repository.AttendanceStatusRepository;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.mappers.AttendanceMapper;
import kr.co.monitoringserver.service.mappers.AttendanceStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceStatusService {

    /** 출석 상태 서비스
     *  출석 기록이 생성될 때마다 호출되어 출석 상태를 업데이트하는 역할
     */

    private final AttendanceStatusRepository attendanceStatusRepository;

    private final AttendanceStatusMapper attendanceStatusMapper;

    /** Create Attendance Status Service
     *
     */
    @Transactional
    public void createAttendanceStatus(AttendStatusReqDTO.CREATE create) {

        AttendanceStatus attendanceStatus = attendanceStatusMapper.toAttendStatusEntity(create);

        attendanceStatusRepository.save(attendanceStatus);
    }

}
