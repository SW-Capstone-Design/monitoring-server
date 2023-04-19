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

        private String attendanceStatus;    // 입력받는 형식이 아닌 입/출력 시간을 계산하여 출력되도록

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
