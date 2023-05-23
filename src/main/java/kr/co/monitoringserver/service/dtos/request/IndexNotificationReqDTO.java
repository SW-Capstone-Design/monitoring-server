package kr.co.monitoringserver.service.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndexNotificationReqDTO {
    private Long indexAlertId;
    private String indexAlertContent;
    private Timestamp indexAlertTime;
}
