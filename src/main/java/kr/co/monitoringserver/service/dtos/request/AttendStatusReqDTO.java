package kr.co.monitoringserver.service.dtos.request;

import jakarta.validation.constraints.NotNull;
import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.*;

public class AttendStatusReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        @NotNull(message = "Please enter your attendance status id")
        private Long attendanceStatusId;

        private AttendanceType attendanceType;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        @NotNull(message = "Please enter your attendance status id")
        private Long attendanceStatusId;

        private String attendanceType;
    }
}
