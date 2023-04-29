package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.attendance.Attendance;
import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserAttendanceMapper {

    default UserAttendance toUserAttendanceEntity(User user,
                                          AttendanceReqDTO.CREATE create,
                                          AttendanceType goWork,
                                          AttendanceType leaveWork) {

        return UserAttendance.builder()
                .user(user)
                .attendance(Attendance.builder()
                        .enterTime(create.getEnterTime())
                        .leaveTime(create.getLeaveTime())
                        .goWork(goWork)
                        .leaveWork(leaveWork)
                        .date(create.getDate())
                        .build())
                .build();
    }

    // UserAttendance Entity -> AttendanceResDTO.READ
    @Mapping(source = "userAttendance.user.identity", target = "userIdentity")
    @Mapping(source = "userAttendance.user.name", target = "userName")
    @Mapping(source = "userAttendance.user.department", target = "userDepartment")
    @Mapping(source = "userAttendance.user.roleType", target = "userRoleType")
    @Mapping(source = "userAttendance.attendance.enterTime", target = "enterTime")
    @Mapping(source = "userAttendance.attendance.leaveTime", target = "leaveTime")
    @Mapping(source = "userAttendance.attendance.goWork", target = "goWork")
    @Mapping(source = "userAttendance.attendance.leaveWork", target = "leaveWork")
    @Mapping(source = "userAttendance.attendance.date", target = "date")
    @Mapping(source = "attendanceDays", target = "attendanceDays")
    AttendanceResDTO.READ toUserAttendacneReadDto(UserAttendance userAttendance, Map<AttendanceType, Integer> attendanceDays);

    default List<AttendanceResDTO.READ> toUserAttendanceReadDtoList(List<UserAttendance> userAttendances,
                                                                    Map<AttendanceType, Integer> attendanceDays) {

        return userAttendances.stream()
                .map(userAttendance -> toUserAttendacneReadDto(userAttendance, attendanceDays))
                .collect(Collectors.toList());
    }

    @Mapping(source = "userAttendance.attendance.goWork", target = "goWork")
    @Mapping(source = "userAttendance.attendance.enterTime", target = "enterTime")
    @Mapping(source = "userAttendance.attendance.date", target = "date")
    AttendanceResDTO.READ toAttendTypeReadDto(UserAttendance userAttendance);
}
