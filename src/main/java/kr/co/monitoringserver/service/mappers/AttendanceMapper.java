package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.persistence.entity.UserAttendance;
import kr.co.monitoringserver.service.enums.AttendanceType;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    // UserAttendanceReqDTO.CREATE DTO -> Attendance Entity
//    @BeforeMapping
//    default void setUser(@MappingTarget Attendance attendance, @Autowired UserRepository userRepository, UserAttendanceReqDTO.CREATE create) {
//
//        final User user = userRepository.findByIdentity()
//    }
//    @Mapping(source = "create.enterTime", target = "enterTime")
//    @Mapping(source = "create.leaveTime", target = "leaveTime")
//    @Mapping(source = "create.date", target = "date")
//    @Mapping(source = "goWorkType", target = "goWork")
//    @Mapping(source = "leaveWorkType", target = "leaveWork")
//    @Mapping(target = "attendanceDays", ignore = true)
//
//    Attendance toAttendanceStatusEntity(UserAttendanceReqDTO.CREATE create,
//                                        AttendanceType goWorkType,
//                                        AttendanceType leaveWorkType,
//                                        User user);

    // Attendance Entity -> AttendanceResDTO.READ DTO
//    default AttendanceResDTO.READ toAttendStatusReadDto(List<Attendance> attendances,
//                                                        Map<AttendanceType, Integer> attendanceDays,
//                                                        List<UserAttendance> userAttendances) {
//
//
//        return AttendanceResDTO.READ.builder()
//                .userId(userAttendances.get(0).getUser().getUserId())
//                .userName(userAttendances.get(0).getUser().getName())
//                .enterTime(attendances.get(0).getEnterTime())
//                .leaveTime(attendances.get(0).getLeaveTime())
//                .goWork(attendances.get(0).getGoWork())
//                .leaveWork(attendances.get(0).getLeaveWork())
//                .date(attendances.get(0).getDate())
//                .attendanceDays(attendanceDays) // 추가된 출석 일수 정보
//                .build();
//    }

//    @Mapping(source = "goWork", target = "goWork")
//    @Mapping(source = "enterTime", target = "enterTime")
//    @Mapping(source = "date", target = "date")
//    AttendanceResDTO.READ toAttendTypeReadDto(Attendance attendance);
}
