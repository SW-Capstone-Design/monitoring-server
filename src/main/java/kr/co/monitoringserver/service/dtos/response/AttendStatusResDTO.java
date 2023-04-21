package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.persistence.entity.Attendance;
import lombok.*;

import java.util.List;

public class AttendStatusResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private List<Attendance> attendances;

        private String attendanceType;
    }
}
