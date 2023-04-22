package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.BaseEntity;
import lombok.*;

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
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_status_id", nullable = false)
    private AttendanceStatus attendanceStatus;



    @Builder
    private Attendance(User user,
                       AttendanceStatus attendanceStatus) {

        this.user = user;
        this.attendanceStatus = attendanceStatus;
    }
}
