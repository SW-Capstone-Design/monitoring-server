package kr.co.monitoringserver.service.dtos.request;

import jakarta.validation.constraints.NotBlank;
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

        @NotBlank(message = "수정이 필요한 날짜를 입력해주세요")
        private LocalDate date;
    }
}