package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendStatusResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceStatusMapper {

    // AttendStatusReqDTO.CREATE DTO -> AttendanceStatus Entity
    @Mapping(source = "create.attendanceType", target = "attendanceType")
    @Mapping(source = "attendances", target = "attendances")
    AttendanceStatus toAttendStatusEntity(AttendStatusReqDTO.CREATE create, List<Attendance> attendances);

//    @Mapping(source = "create.description", target = "description")
//    @Mapping(source = "create.lateCount", target = "lateCount")
//    @Mapping(source = "create.absentCount", target = "absentCount")
//    @Mapping(source = "create.attendanceType", target = "attendanceType")
//    AttendanceStatus toAttendStatusEntity(AttendStatusReqDTO.CREATE create);

    // AttendanceStatus Entity -> AttendStatusResDTO.READ DTO
//    @Mapping(source = "attendanceStatus.attendanceType", target = "attendanceType")
//    AttendStatusResDTO.READ toAttendStatusReadDto(AttendanceStatus attendanceStatus);

    // AttendStatusReqDTO.UPDATE DTO -> AttendanceStatus Entity
//    @Mapping(source = "oldStatus", target = "attendanceStatusId")
//    @Mapping(source = "update.", target = "")
//    @Mapping(source = "", target = "")
//    @Mapping(source = "", target = "")
//    @Mapping(source = "", target = "")
//    @Mapping(source = "", target = "")
//    @Mapping(source = "", target = "")
//    AttendanceStatus toUpdatedAttendStatusEntity(AttendanceStatus oldStatus, AttendStatusReqDTO.UPDATE update);
}
