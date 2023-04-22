package kr.co.monitoringserver.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AttendanceType {

    GO_WORK("출근", 0),

    LEAVE_WORK("퇴근", 0),

    ABSENT("결근", 0),

    TARDINESS("지각", 0),

    EARLY_LEAVE("조퇴", 0),

    ATTENDANCE("정상 근무", 0);



    String attendanceType;

    int count;



//    public static AttendanceType of(String attendanceType){
//        return Arrays.stream(AttendanceType.values())
//                .filter(type -> type.toString().equalsIgnoreCase(attendanceType))
//                .findAny().orElseThrow(() -> new RuntimeException("Not Found AttendanceType"));
//    }
}
