package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    // AttendanceReqDTO.CREATE -> Attendance Entity
//    @Mapping(source = "create.enterTime", target = "enterTime")
//    @Mapping(source = "create.leaveTime", target = "leaveTime")
//    @Mapping(source = "create.userId", target = "user.userId")
//    @Mapping(source = "attendanceStatus", target = "attendanceStatus")
//    Attendance toAttendacneEntity(AttendanceReqDTO.CREATE create, AttendanceStatus attendanceStatus, User user);

    // Attendance Entity -> AttendanceResDTO.READ
    @Mapping(source = "attendance.user.identity", target = "userIdentity")
    @Mapping(source = "attendance.user.name", target = "userName")
    @Mapping(source = "attendance.user.department", target = "userDepartment")
    @Mapping(source = "attendance.user.roleType", target = "userRoleType")
    @Mapping(source = "attendance.attendanceStatus.enterTime", target = "enterTime")
    @Mapping(source = "attendance.attendanceStatus.leaveTime", target = "leaveTime")
    @Mapping(source = "attendance.attendanceStatus.goWork", target = "goWorkType")
    @Mapping(source = "attendance.attendanceStatus.leaveWork", target = "leaveWorkType")
    @Mapping(source = "attendance.attendanceStatus.date", target = "date")
    @Mapping(source = "attendance.attendanceStatus.attendanceDays", target = "attendanceDays")
    AttendanceResDTO.READ toAttendacneReadDto(Attendance attendance);
}
