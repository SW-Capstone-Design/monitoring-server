package kr.co.monitoringserver.service.dtos.request;

import lombok.*;

import java.time.LocalDate;

public class AttendanceReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        private LocalDate enterTime;

        private LocalDate leaveTime;

        private String attendanceStatus;

        private String userIdentity;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        private String attendanceStatus;

        private Long userId;
    }
}
