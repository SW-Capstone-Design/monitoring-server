package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.BaseEntity;
import kr.co.monitoringserver.service.dtos.request.AttendStatusReqDTO;
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
@Table(name = "tbl_attendance_status")
@AttributeOverride(
        name = "id",
        column = @Column(name = "attendance_status_id", length = 4))
public class AttendanceStatus extends BaseEntity {

    /** 출석 상태 엔티티
     *  출석 상태에 대한 정보(출석, 결석, 지각, 조퇴 등)를 저장하는 역할
     *  즉, 출석 상태와 해당 상태에 대한 설명을 갖음
     */

    @Column(name = "enter_time")
    private LocalTime enterTime;    // 출근 시간

    @Column(name = "leave_time")
    private LocalTime leaveTime;    // 퇴근 시간

    @Column(name = "date")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    private AttendanceStatus(AttendanceType goWork,
                             AttendanceType leaveWork,
                             LocalTime enterTime,
                             LocalTime leaveTime,
                             LocalDate date,
                             Map<AttendanceType, Integer> attendanceDays,
                             User user) {

        this.goWork = goWork;
        this.leaveWork = leaveWork;
        this.enterTime = enterTime;
        this.leaveTime = leaveTime;
        this.date = date;
        this.attendanceDays = attendanceDays;
        this.user = user;
    }

    public void updateAttendanceStatus(AttendStatusReqDTO.UPDATE update,
                                       AttendanceType goWork,
                                       AttendanceType leaveWork) {

        this.enterTime = update.getEnterTime();
        this.leaveTime = update.getLeaveTime();
        this.date = update.getDate();
        this.goWork = goWork;
        this.leaveWork = leaveWork;
    }
}
