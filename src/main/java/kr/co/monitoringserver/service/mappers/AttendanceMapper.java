package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    // Attendance Entity -> AttendanceResDTO.READ
    default AttendanceResDTO.READ toAttendacneReadDto(AttendanceStatus attendanceStatus) {

        return AttendanceResDTO.READ.builder()
                .userIdentity(attendanceStatus.getUser().getIdentity())
                .userName(attendanceStatus.getUser().getName())
                .userDepartment(attendanceStatus.getUser().getDepartment())
                .userRoleType(attendanceStatus.getUser().getRoleType())
                .enterTime(attendanceStatus.getEnterTime())
                .leaveTime(attendanceStatus.getLeaveTime())
                .goWorkType(attendanceStatus.getGoWork())
                .leaveWorkType(attendanceStatus.getLeaveWork())
                .date(attendanceStatus.getDate())
                .attendanceDays(attendanceStatus.getAttendanceDays())
                .build();
    }
}
