package kr.co.monitoringserver.persistence.entity.securityArea;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import kr.co.monitoringserver.persistence.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_security_access_log")
@AttributeOverride(
        name = "id",
        column = @Column(name = "security_access_log_id", length = 4))
public class SecurityAccessLog extends BaseEntity {

    @Column(name = "access_time")
    private LocalTime accessTime;

    @ManyToOne
    @JoinColumn(name = "security_area_id")
    private SecurityArea securityArea;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    private SecurityAccessLog(LocalTime accessTime,
                              SecurityArea securityArea,
                              User user) {

        this.accessTime = accessTime;
        this.securityArea = securityArea;
        this.user = user;
    }
}
