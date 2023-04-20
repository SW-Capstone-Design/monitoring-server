package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.BaseEntity;
import kr.co.monitoringserver.service.enums.AttendanceType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_attendances_status")
@AttributeOverride(
        name = "id",
        column = @Column(name = "attendance_status_id", length = 4))
public class AttendanceStatus extends BaseEntity {

    /** 출석 상태 엔티티
     *  출석 상태에 대한 정보(출석, 결석, 지각, 조퇴 등)를 저장하는 역할
     *  즉, 출석 상태와 해당 상태에 대한 설명을 갖음
     */

    @Enumerated(value = EnumType.STRING)
    @Column(name = "attendance_type")
    private AttendanceType attendanceType;

    @Column(name = "description")
    private String description;

    @Column(name = "late_count", nullable = false)
    private int lateCount;

    @Column(name = "absent_count", nullable = false)
    private int absentCount;

    @OneToMany(
            mappedBy = "attendanceStatus",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Attendance> attendances = new ArrayList<>();


    @Builder
    private AttendanceStatus(AttendanceType attendanceType,
                             String description,
                             int lateCount,
                             int absentCount,
                             List<Attendance> attendances) {

        this.attendanceType = attendanceType;
        this.description = description;
        this.lateCount = lateCount;
        this.absentCount = absentCount;
        this.attendances = attendances;
    }
}
