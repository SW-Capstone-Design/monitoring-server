package kr.co.monitoringserver.persistence.entity.attendance;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_attendance")
@AttributeOverride(
        name = "id",
        column = @Column(name = "attendance_id", length = 4))
public class Attendance extends BaseEntity {

    @Column(name = "enter_time")
    private LocalTime enterTime;    // 출근 시간

    @Column(name = "leave_time")
    private LocalTime leaveTime;    // 퇴근 시간

    @Column(name = "date", length = 30)
    private LocalDate date;         // 출근 일자

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "attendance_days")
    private Map<AttendanceType, Integer> attendanceDays;    // 출석 일수

    @Enumerated(value = EnumType.STRING)
    @Column(name = "go_work")
    private AttendanceType goWork;  // 출근 상태 종류

    @Enumerated(value = EnumType.STRING)
    @Column(name = "leave_work")
    private AttendanceType leaveWork;  // 퇴근 상태 종류


    @Builder
    private Attendance(AttendanceType goWork,
                       AttendanceType leaveWork,
                       LocalTime enterTime,
                       LocalTime leaveTime,
                       LocalDate date,
                       Map<AttendanceType, Integer> attendanceDays) {

        this.goWork = goWork;
        this.leaveWork = leaveWork;
        this.enterTime = enterTime;
        this.leaveTime = leaveTime;
        this.date = date;
        this.attendanceDays = attendanceDays;
    }
}
