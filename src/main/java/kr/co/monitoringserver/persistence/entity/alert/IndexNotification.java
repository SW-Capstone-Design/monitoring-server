package kr.co.monitoringserver.persistence.entity.alert;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "tbl_index_notification")
public class IndexNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_alert_id")
    private Long indexAlertId;

    @CreationTimestamp
    @Column(name = "index_alert_time")
    private Timestamp indexAlertTime;

    @Column(name = "index_alert_content")
    private String indexAlertContent;
}
