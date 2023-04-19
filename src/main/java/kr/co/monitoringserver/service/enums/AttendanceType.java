package kr.co.monitoringserver.service.enums;

import lombok.Getter;

@Getter
public enum AttendanceType {

    GO_WORK("출석"),

    LEAVE_WORD("퇴근"),

    ABSENT("결석"),

    TARDINESS("지각"),

    EARLY_LEAVE("조퇴");

    private final String name;

    AttendanceType(String name) {
        this.name = name;
    }
}
