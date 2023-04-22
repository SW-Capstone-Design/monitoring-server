package kr.co.monitoringserver.service.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.monitoringserver.persistence.entity.AttendanceStatus;
import kr.co.monitoringserver.service.enums.RoleType;
import lombok.*;

import java.time.LocalTime;

public class AttendanceReqDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CREATE {

        @NotNull(message = "Please enter your user id")
        private Long userId;

        @NotNull(message = "Please enter your attendance status id")
        private Long attendanceStatusId;

        private LocalTime enterTime;

        private LocalTime leaveTime;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        @NotNull(message = "Please enter your attendance id")
        private Long attendanceId;

        @NotNull(message = "Please enter your user id")
        private Long userId;

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private AttendanceStatus attendanceStatus;

        @NotBlank(message = "Please enter your role type")
        private RoleType roleType;
    }
}
