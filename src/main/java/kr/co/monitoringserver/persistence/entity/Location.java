package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Location {

    private double x;

    private double y;


    @Builder
    public Location(double x,
                    double y) {

        this.x = x;
        this.y = y;
    }

    public double[] positionArray() {

        return new double[]{x, y};
    }
}
