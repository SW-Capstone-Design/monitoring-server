package kr.co.monitoringserver.service.dtos.request;

import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.*;

import java.time.LocalTime;

public class AttendStatusReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        private AttendanceType attendanceType;

        private Long attendanceStatusId;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        private Long attendanceStatusId;

        private Long userId;

        private Long attendanceId;

        private String description;

        private LocalTime attendanceTime;

        private AttendanceType attendanceType;
    }
}
