package kr.co.monitoringserver.service.dtos.request;

import kr.co.monitoringserver.service.enums.AttendanceType;
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

        private Long userId;

        private AttendanceType attendanceType;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        private Long userId;

        private LocalDate attendanceDate;
    }
}
