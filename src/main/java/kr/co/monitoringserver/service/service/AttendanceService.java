package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.entity.attendance.UserAttendance;
import kr.co.monitoringserver.service.enums.AttendanceType;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class AttendanceService {

    /**
     * 출근 시간 출석 상태 계산
     */
    public AttendanceType calculateGoWorkAttendanceType(LocalTime enterTime) {

        LocalTime startTime = LocalTime.parse("08:00:00");

        if (enterTime == null) {
            return AttendanceType.ABSENT;
        } else if (enterTime.isBefore(startTime)) {
            return AttendanceType.GO_WORK;
        } else {
            return AttendanceType.TARDINESS;
        }
    }

    /**
     * 퇴근 시간 출석 상태 계산
     */
    public AttendanceType calculateLeaveWorkAttendanceType(LocalTime leaveTime) {

        LocalTime endTime = LocalTime.parse("17:00:00");

        if (leaveTime == null) {
            return AttendanceType.ABSENT;
        } else if (leaveTime.isAfter(endTime)) {
            return AttendanceType.LEAVE_WORK;
        } else {
            return AttendanceType.EARLY_LEAVE;
        }
    }

    /**
     * 사용자의 출석 상태가 지각인지 검사
     */
    public boolean isLate(UserAttendance userAttendance) {

        return userAttendance.getAttendance().getGoWork() == AttendanceType.TARDINESS;
    }

    /**
     * 사용자의 출석 상태가 결근인지 검사
     */
    public boolean isAbsent(UserAttendance userAttendance) {

        return userAttendance.getAttendance().getGoWork() == AttendanceType.ABSENT
                || userAttendance.getAttendance().getLeaveWork() == AttendanceType.ABSENT;
    }
}
