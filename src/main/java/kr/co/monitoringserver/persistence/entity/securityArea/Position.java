package kr.co.monitoringserver.persistence.entity.securityArea;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Position {

    private Double latitude;    // 경도

    private Double longitude;   // 위도


    @Builder
    public Position(Double latitude,
                    Double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }
}
