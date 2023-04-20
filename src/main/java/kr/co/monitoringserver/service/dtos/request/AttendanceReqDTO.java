package kr.co.monitoringserver.service.dtos.request;

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

        private LocalTime enterTime;

        private LocalTime leaveTime;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UPDATE {

        private Long attendanceId;

        private Long userId;

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private AttendanceStatus attendanceStatus;

        private RoleType roleType;
    }
}
