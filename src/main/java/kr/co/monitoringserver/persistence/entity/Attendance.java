package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.BaseEntity;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import lombok.*;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_attendances")
@AttributeOverride(
        name = "id",
        column = @Column(name = "attendance_id", length = 4))
public class Attendance extends BaseEntity {

    /** 출석 엔티티
     *  특정 사용자의 출석 기록을 저장하는 역할
     *  즉, 사용자와 날짜 정보를 갖음
     */
    @Column(name = "enter_time")
    private LocalTime enterTime;    // 출근 시간

    @Column(name = "leave_time")
    private LocalTime leaveTime;    // 퇴근 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_status_id", nullable = false)
    private AttendanceStatus attendanceStatus;



    @Builder
    private Attendance(LocalTime enterTime,
                       LocalTime leaveTime,
                       User user,
                       AttendanceStatus attendanceStatus) {

        this.enterTime = enterTime;
        this.leaveTime = leaveTime;
        this.user = user;
        this.attendanceStatus = attendanceStatus;
    }


    public void updateAttendance(AttendanceReqDTO.UPDATE update) {

        this.enterTime = update.getEnterTime();
        this.leaveTime = update.getLeaveTime();
        this.attendanceStatus = update.getAttendanceStatus();
    }
}
