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

    // ============UserAttendance============//

    default UserAttendance toUserAttendanceEntity(User user, Attendance attendance) {

        return UserAttendance.builder()
                .user(user)
                .attendance(attendance)
                .build();
    }

    @Mapping(source = "user.identity", target = "userIdentity")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.department", target = "userDepartment")
    @Mapping(source = "user.userRoleType", target = "userRoleType")
    @Mapping(source = "attendance.enterTime", target = "enterTime")
    @Mapping(source = "attendance.leaveTime", target = "leaveTime")
    @Mapping(source = "attendance.goWork", target = "goWork")
    @Mapping(source = "attendance.leaveWork", target = "leaveWork")
    @Mapping(source = "attendance.date", target = "date")
    AttendanceResDTO.READ toUserAttendanceReadDto(UserAttendance userAttendance);


    @Mapping(source = "attendance.goWork", target = "goWork")
    @Mapping(source = "attendance.enterTime", target = "enterTime")
    @Mapping(source = "attendance.date", target = "date")
    AttendanceResDTO.READ toAttendTypeReadDto(UserAttendance userAttendance);
}
