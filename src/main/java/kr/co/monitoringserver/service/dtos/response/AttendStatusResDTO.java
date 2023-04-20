package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.persistence.entity.Attendance;
import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.*;

public class AttendStatusResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private Attendance attendance;

        private AttendanceType attendanceType;
    }
}
