package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    // AttendanceReqDTO.CREATE -> Attendance Entity
    @Mapping(source = "create.enterTime", target = "enterTime")
    @Mapping(source = "create.leaveTime", target = "leaveTime")
    @Mapping(source = "attendanceType", target = "attendanceStatus.attendanceType")
    Attendance toAttendacneEntity(AttendanceReqDTO.CREATE create, AttendanceType attendanceType);

    // Attendance Entity -> AttendanceResDTO.READ
    @Mapping(source = "attendance.enterTime", target = "enterTime")
    @Mapping(source = "attendance.leaveTime", target = "leaveTime")
    @Mapping(source = "attendance.user.name", target = "userName")
    AttendanceResDTO.READ toAttendacneReadDto(Attendance attendance);
}
