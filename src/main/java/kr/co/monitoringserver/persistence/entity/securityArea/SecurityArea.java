package kr.co.monitoringserver.persistence.entity.securityArea;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import kr.co.monitoringserver.persistence.entity.alert.SecurityAreaWarning;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
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
        column = @Column(name = "security_area_id", length = 4))
public class SecurityArea extends BaseEntity {

    @Column(name = "security_area_name",
            nullable = false,
            length = 30)
    private String name;

    @Column(name = "security_area_description",
            nullable = false)
    private String description;

    @Embedded
    @Column(name = "security_area_location",
            nullable = false)
    private Position securityAreaLocation;

    @OneToMany(mappedBy = "securityArea")
    private List<UserSecurityArea> userSecurityAreas = new ArrayList<>();

    @OneToMany(mappedBy = "securityArea")
    private List<SecurityAreaWarning> securityAreaWarnings = new ArrayList<>();


    @Builder
    private SecurityArea(String name,
                         String description,
                         Position securityAreaLocation,
                         List<SecurityAreaWarning> securityAreaWarnings) {

        this.name = name;
        this.description = description;
        this.securityAreaLocation = securityAreaLocation;
        this.securityAreaWarnings = securityAreaWarnings;
    }

    public void updateSecurityArea(SecurityAreaReqDTO.UPDATE update) {

        this.name = update.getName();
        this.description = update.getDescription();
        this.securityAreaLocation = update.getLocation();
    }
}
