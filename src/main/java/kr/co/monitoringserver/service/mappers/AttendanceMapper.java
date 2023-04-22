package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    // Attendance Entity -> AttendanceResDTO.READ
    default AttendanceResDTO.READ toAttendacneReadDto(Attendance attendance) {

        return AttendanceResDTO.READ.builder()
                .userIdentity(attendance.getUser().getIdentity())
                .userName(attendance.getUser().getName())
                .userDepartment(attendance.getUser().getDepartment())
                .userRoleType(attendance.getUser().getRoleType())
                .enterTime(attendance.getAttendanceStatus().getEnterTime())
                .leaveTime(attendance.getAttendanceStatus().getLeaveTime())
                .goWorkType(attendance.getAttendanceStatus().getGoWork())
                .leaveWorkType(attendance.getAttendanceStatus().getLeaveWork())
                .date(attendance.getAttendanceStatus().getDate())
                .attendanceDays(attendance.getAttendanceStatus().getAttendanceDays())
                .build();
    }
}
