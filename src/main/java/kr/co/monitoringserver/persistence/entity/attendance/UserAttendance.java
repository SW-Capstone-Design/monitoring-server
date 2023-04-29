package kr.co.monitoringserver.persistence.entity.attendance;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_user_attendance")
@AttributeOverride(
        name = "id",
        column = @Column(name = "user_attendance_id", length = 4))
public class UserAttendance extends BaseEntity {

    /** 출석 엔티티
     *  User, Attendance 테이블의 다대다 매핑 중간 테이블
     *  Entity 로 승격시켜 단방향으로만 맵핑
     *  특정 사용자의 출석 기록을 저장하는 역할
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "attendance_status_id", nullable = false)
    private Attendance attendance;



    @Builder
    private UserAttendance(User user,
                           Attendance attendance) {

        this.user = user;
        this.attendance = attendance;
    }


    public void updateAttendance(AttendanceReqDTO.UPDATE update,
                                 AttendanceType goWork,
                                 AttendanceType leaveWork) {

        this.attendance = Attendance.builder()
                .enterTime(update.getEnterTime())
                .leaveTime(update.getLeaveTime())
                .goWork(goWork)
                .leaveWork(leaveWork)
                .date(update.getDate())
                .build();
    }
}
