package kr.co.monitoringserver.service.dtos.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        private LocalTime enterTime;

        private LocalTime leaveTime;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        private Long userId;

        private LocalDate attendanceDate;

        private LocalTime enterTime;

        private LocalTime leaveTime;
    }
}
