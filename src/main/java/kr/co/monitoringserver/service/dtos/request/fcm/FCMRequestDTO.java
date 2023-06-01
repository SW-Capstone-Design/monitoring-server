package kr.co.monitoringserver.service.dtos.request.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FCMRequestDTO {
    private String targetToken;
    private String title;
    private String body;
}
