package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.UserAttendance;
import kr.co.monitoringserver.persistence.entity.User;
import kr.co.monitoringserver.service.dtos.request.UserAttendanceReqDTO;
import kr.co.monitoringserver.service.dtos.response.UserAttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserAttendanceMapper {

    default UserAttendance toUserAttendanceEntity(User user,
                                          UserAttendanceReqDTO.CREATE create,
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

    // UserAttendance Entity -> UserAttendanceResDTO.READ
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
    UserAttendanceResDTO.READ toUserAttendacneReadDto(UserAttendance userAttendance, Map<AttendanceType, Integer> attendanceDays);

    default List<UserAttendanceResDTO.READ> toUserAttendanceReadDtoList(List<UserAttendance> userAttendances,
                                                                        Map<AttendanceType, Integer> attendanceDays) {

        return userAttendances.stream()
                .map(userAttendance -> toUserAttendacneReadDto(userAttendance, attendanceDays))
                .collect(Collectors.toList());
    }

    @Mapping(source = "userAttendance.attendance.goWork", target = "goWork")
    @Mapping(source = "userAttendance.attendance.enterTime", target = "enterTime")
    @Mapping(source = "userAttendance.attendance.date", target = "date")
    UserAttendanceResDTO.READ toAttendTypeReadDto(UserAttendance userAttendance);
}
