package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.enums.RoleType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class AttendanceResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private String userIdentity;

        private String userName;

        private String userDepartment;

        private RoleType userRoleType;

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private AttendanceType goWorkType;

        private AttendanceType leaveWorkType;

        private LocalDate date;

        private Map<AttendanceType, Integer> attendanceDays;
    }
}
