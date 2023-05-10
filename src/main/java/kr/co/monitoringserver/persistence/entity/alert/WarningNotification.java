package kr.co.monitoringserver.persistence.entity.alert;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_warning_notification")
@AttributeOverride(
        name = "id",
        column = @Column(name = "warning_notification_id", length = 4))
public class WarningNotification extends BaseEntity {

    @Column(name = "warning_time")
    private LocalTime time;

    @Column(name = "warning_content",
            length = 50)
    private String content;


    @Builder
    private WarningNotification(LocalTime time,
                                String content) {

        this.time = time;
        this.content = content;
    }
}
