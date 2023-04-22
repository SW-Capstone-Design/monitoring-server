package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    // AttendanceReqDTO.CREATE -> Attendance Entity
    @Mapping(source = "create.enterTime", target = "enterTime")
    @Mapping(source = "create.leaveTime", target = "leaveTime")
    @Mapping(source = "create.userId", target = "user.userId")
    @Mapping(source = "attendanceStatus", target = "attendanceStatus")
    Attendance toAttendacneEntity(AttendanceReqDTO.CREATE create, AttendanceStatus attendanceStatus);

    // Attendance Entity -> AttendanceResDTO.READ
    @Mapping(source = "attendance.enterTime", target = "enterTime")
    @Mapping(source = "attendance.leaveTime", target = "leaveTime")
    @Mapping(source = "attendance.attendanceStatus", target = "attendanceStatus")
    @Mapping(source = "attendance.user.name", target = "userName")
    AttendanceResDTO.READ toAttendacneReadDto(Attendance attendance);

    @Mapping(source = "attendance.enterTime", target = "enterTime")
    @Mapping(source = "attendance.leaveTime", target = "leaveTime")
    @Mapping(source = "attendance.user.name", target = "userName")
    @Mapping(source = "attendance.user.department", target = "userDepartment")
    @Mapping(source = "attendance.user.roleType", target = "userRoleType")
    @Mapping(source = "attendance.user.telephone", target = "userTelephone")
    AttendanceResDTO.READ_DETAIL toAttendanceReadDetailDto(Attendance attendance);
}
