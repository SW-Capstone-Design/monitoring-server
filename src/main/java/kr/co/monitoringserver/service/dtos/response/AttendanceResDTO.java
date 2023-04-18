package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.service.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;

public class AttendanceResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private LocalDate enterTime;

        private LocalDate leaveTime;

        private AttendanceStatus attendanceStatus;

        private String userName;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ_DETAIL {

        private LocalDate enterTime;

        private LocalDate leaveTime;

        private AttendanceStatus attendanceStatus;

        private String userName;

        private String userDepartment;

        private String userRoleType;

        private String userPhone;
    }
}
