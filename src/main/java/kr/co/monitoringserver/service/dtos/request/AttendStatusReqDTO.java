package kr.co.monitoringserver.service.dtos.request;

import jakarta.validation.constraints.NotNull;
import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendStatusReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private LocalDate date;

        @NotNull(message = "Please enter your user id")
        private Long userId;
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

        @NotNull(message = "Please enter your user id")
        private Long userId;
    }
}
