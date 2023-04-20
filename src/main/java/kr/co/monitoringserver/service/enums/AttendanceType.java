package kr.co.monitoringserver.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AttendanceType {

    GO_WORK("출석"),

    LEAVE_WORK("퇴근"),

    ABSENT("결석"),

    TARDINESS("지각"),

    EARLY_LEAVE("조퇴");

    String attendanceType;

    public static AttendanceType of(String attendanceType){
        return Arrays.stream(AttendanceType.values())
                .filter(type -> type.toString().equalsIgnoreCase(attendanceType))
                .findAny().orElseThrow(() -> new RuntimeException("Not Found AttendanceType"));
    }
}
