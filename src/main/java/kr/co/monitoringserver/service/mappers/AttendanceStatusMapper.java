package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.persistence.entity.User;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendStatusResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface AttendanceStatusMapper {

    // AttendStatusReqDTO.CREATE DTO -> AttendanceStatus Entity
    @Mapping(source = "create.enterTime", target = "enterTime")
    @Mapping(source = "create.leaveTime", target = "leaveTime")
    @Mapping(source = "create.date", target = "date")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "goWorkType", target = "goWork")
    @Mapping(source = "leaveWorkType", target = "leaveWork")
    AttendanceStatus toAttendStatusEntity(AttendStatusReqDTO.CREATE create, User user, AttendanceType goWorkType, AttendanceType leaveWorkType);

    // AttendanceStatus Entity -> AttendStatusResDTO.READ DTO
    default AttendStatusResDTO.READ toAttendStatusReadDto(AttendanceStatus attendanceStatus, Map<AttendanceType, Integer> attendanceDays, User user) {

        return AttendStatusResDTO.READ.builder()
                .userId(user.getUserId())
                .userName(user.getName())
                .enterTime(attendanceStatus.getEnterTime())
                .leaveTime(attendanceStatus.getLeaveTime())
                .goWork(attendanceStatus.getGoWork())
                .leaveWork(attendanceStatus.getLeaveWork())
                .date(attendanceStatus.getDate())
                .attendanceDays(attendanceDays) // 추가된 출석 일수 정보
                .build();
    }
}
