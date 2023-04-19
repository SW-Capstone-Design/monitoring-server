package kr.co.monitoringserver.service.dtos.request;

import lombok.*;

import java.time.LocalTime;

public class AttendanceReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private String userIdentity;

        private String attendanceStatusType;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        private Long userId;
    }
}
