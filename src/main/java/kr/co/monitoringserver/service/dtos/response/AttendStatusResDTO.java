package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class AttendStatusResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private Long userId;

        private String userName;

        private AttendanceType goWork;

        private AttendanceType leaveWork;

        private LocalTime enterTime;

        private LocalTime leaveTime;

        private LocalDate date;

        private Map<AttendanceType, Integer> attendanceDays;
    }
}
