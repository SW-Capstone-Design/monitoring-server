package kr.co.monitoringserver.persistence.entity.securityArea;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.alert.SecurityAreaWarning;
import kr.co.monitoringserver.service.dtos.request.securityArea.SecurityAreaReqDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_security_area")
@AttributeOverride(
        name = "id",
        column = @Column(name = "security_area_id", length = 4)
)
public class SecurityArea extends BaseEntity {

    @Column(name = "security_area_name",
            nullable = false,
            length = 30)
    private String name;

    @Column(name = "security_area_description",
            nullable = false)
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "lower_left_corner_x", nullable = false)),
            @AttributeOverride(name = "y", column = @Column(name = "lower_left_corner_y", nullable = false))
    })
    private Location lowerLeft;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "upper_right_corner_x", nullable = false)),
            @AttributeOverride(name = "y", column = @Column(name = "upper_right_corner_y", nullable = false))
    })
    private Location upperRight;

    @OneToMany(mappedBy = "securityArea",
               cascade = CascadeType.REMOVE)
    private List<UserSecurityArea> userSecurityAreas = new ArrayList<>();

    @OneToMany(mappedBy = "securityArea")
    private List<SecurityAreaWarning> securityAreaWarnings = new ArrayList<>();


    @Builder
    private SecurityArea(String name,
                         String description,
                         Location lowerLeft,
                         Location upperRight,
                         List<SecurityAreaWarning> securityAreaWarnings) {

        this.name = name;
        this.description = description;
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.securityAreaWarnings = securityAreaWarnings;
    }


    public void createSecurityAreaLocation(Location lowerLeft,
                                           Location upperRight) {

        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public void updateSecurityArea(SecurityAreaReqDTO.UPDATE update) {

        this.name = update.getName();
        this.description = update.getDescription();

        this.lowerLeft = Location.builder()
                .x(update.getLowerLeft().getX())
                .y(update.getLowerLeft().getY())
                .build();

        this.upperRight = Location.builder()
                .x(update.getUpperRight().getX())
                .y(update.getUpperRight().getY())
                .build();
    }
}
