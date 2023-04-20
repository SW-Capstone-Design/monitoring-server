package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private AttendanceStatus attendanceStatus;

        private String userName;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ_DETAIL {

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private String userName;

        private String userDepartment;

        private String userRoleType;

        private String userTelephone;
    }
}
