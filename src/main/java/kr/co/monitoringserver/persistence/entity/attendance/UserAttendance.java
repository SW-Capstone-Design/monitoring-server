package kr.co.monitoringserver.persistence.entity.attendance;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.service.dtos.request.AttendanceReqDTO;
import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_user_attendance")
@AttributeOverride(
        name = "id",
        column = @Column(name = "user_attendance_id", length = 4))
public class UserAttendance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "attendance_id", nullable = false)
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
