package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.attendance.Attendance;
import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.service.dtos.response.AttendanceResDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {


    default Attendance toAttendanceEntity(AttendanceType goWork) {

        return Attendance.builder()
                .enterTime(LocalTime.now())
                .goWork(goWork)
                .date(LocalDate.now())
                .build();
    }

    default UserAttendance toUserAttendanceEntity(User user, Attendance attendance) {

        return UserAttendance.builder()
                .user(user)
                .attendance(attendance)
                .build();
    }

    @Mapping(source = "userAttendance.user.identity", target = "userIdentity")
    @Mapping(source = "userAttendance.user.name", target = "userName")
    @Mapping(source = "userAttendance.user.department", target = "userDepartment")
    @Mapping(source = "userAttendance.user.roleType", target = "userRoleType")
    @Mapping(source = "userAttendance.attendance.enterTime", target = "enterTime")
    @Mapping(source = "userAttendance.attendance.leaveTime", target = "leaveTime")
    @Mapping(source = "userAttendance.attendance.goWork", target = "goWork")
    @Mapping(source = "userAttendance.attendance.leaveWork", target = "leaveWork")
    @Mapping(source = "userAttendance.attendance.date", target = "date")
    AttendanceResDTO.READ toUserAttendanceReadDto(UserAttendance userAttendance);


    @Mapping(source = "userAttendance.attendance.goWork", target = "goWork")
    @Mapping(source = "userAttendance.attendance.enterTime", target = "enterTime")
    @Mapping(source = "userAttendance.attendance.date", target = "date")
    AttendanceResDTO.READ toAttendTypeReadDto(UserAttendance userAttendance);
}
