package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.BaseEntity;
import kr.co.monitoringserver.service.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_attendance")
@AttributeOverride(
        name = "id",
        column = @Column(name = "attendance_id", length = 4))
public class Attendance extends BaseEntity {

    @Column(name = "enter_time")
    private LocalDate enterTime;

    @Column(name = "leave_time")
    private LocalDate leaveTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "attend_status")
    private AttendanceStatus attendanceStatus;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "users_id")
    private User user;


    @Builder
    private Attendance(LocalDate enterTime,
                       LocalDate leaveTime,
                       AttendanceStatus attendanceStatus,
                       User user) {

        this.enterTime = enterTime;
        this.leaveTime = leaveTime;
        this.attendanceStatus = attendanceStatus;
        this.user = user;
    }

    public void updateAttendance(AttendanceStatus attendanceStatus,
                                 User user) {

        this.attendanceStatus = attendanceStatus;
        this.user = user;
    }
}
