package kr.co.monitoringserver.persistence.entity.alert;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_security_area_alert")
@AttributeOverride(
        name = "id",
        column = @Column(name = "security_area_alert_id", length = 4))
public class SecurityAreaWarning extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "security_area_id")
    private SecurityArea securityArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warning_notification_id")
    private WarningNotification warningNotification;


    @Builder
    private SecurityAreaWarning(SecurityArea securityArea,
                                WarningNotification warningNotification) {

        this.securityArea = securityArea;
        this.warningNotification = warningNotification;
    }
}
