package kr.co.monitoringserver.service.enums;

import lombok.Getter;

@Getter
public enum AttendanceStatus {

    ATTENDANCE("출석"),
    ABSENT("결석"),
    TARDINESS("지각"),
    EARLY_LEAVE("조퇴");

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }
}
