package kr.co.monitoringserver.service.dtos.response;

import lombok.*;

import java.time.LocalTime;

public class AttendanceResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private LocalTime enterTime;

        private LocalTime leaveTime;

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
