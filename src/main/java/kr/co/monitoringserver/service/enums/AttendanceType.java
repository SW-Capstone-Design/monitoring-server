package kr.co.monitoringserver.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttendanceType {

    GO_WORK("출근", 0),

    LEAVE_WORK("퇴근", 0),

    ABSENT("결근", 0),

    TARDINESS("지각", 0),

    EARLY_LEAVE("조퇴", 0);



    String attendanceType;

    int count;
}
