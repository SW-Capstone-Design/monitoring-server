package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendStatusResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceStatusMapper {

    // AttendStatusReqDTO.CREATE -> AttendanceStatus Entity
    @Mapping(source = "create.description", target = "description")
    @Mapping(source = "create.lateCount", target = "lateCount")
    @Mapping(source = "create.absentCount", target = "absentCount")
    @Mapping(source = "create.attendanceType", target = "attendanceType")
    AttendanceStatus toAttendStatusEntity(AttendStatusReqDTO.CREATE create);

    // AttendanceStatus Entity -> AttendStatusResDTO.READ
//    @Mapping(source = "attendanceStatus.attendances.user.identity", target = "userIdentity")
    @Mapping(source = "attendanceStatus.attendanceType", target = "attendanceType")
//    @Mapping(source = "attendanceStatus.attendances.attendanceDate", target = "attendanceDate")
    AttendStatusResDTO.READ toAttendStatusReadDto(AttendanceStatus attendanceStatus);
}
