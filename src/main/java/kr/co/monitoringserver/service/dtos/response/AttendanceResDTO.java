package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.service.enums.AttendanceType;
import kr.co.monitoringserver.service.enums.UserRoleType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private String userIdentity;

        private String userName;

        private String userDepartment;

        private UserRoleType userRoleType;

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private AttendanceType goWork;

        private AttendanceType leaveWork;

        private LocalDate date;
    }
}
