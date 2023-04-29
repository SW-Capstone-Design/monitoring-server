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

        private LocalDate date;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        private AttendanceType goWork;

        private AttendanceType leaveWork;

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private LocalDate date;
    }
}
