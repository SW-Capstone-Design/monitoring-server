package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    // AttendanceReqDTO.CREATE -> Attendance Entity
    @Mapping(source = "create.enterTime", target = "enterTime")
    @Mapping(source = "create.leaveTime", target = "leaveTime")
    @Mapping(source = "create.attendanceStatus", target = "attendanceStatus")
    @Mapping(source = "create.userIdentity", target = "users.identity")
    Attendance toAttendacneEntity(AttendanceReqDTO.CREATE create);

    // Attendance Entity -> AttendanceResDTO.READ
    AttendanceResDTO.READ toAttendacneReadDto(Attendance attendance);
}
