package kr.co.monitoringserver.persistence.entity.securityArea;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Position location;


    @Builder
    private SecurityArea(String name,
                         String description,
                         Position location) {

        this.name = name;
        this.description = description;
        this.location = location;
    }

    public void updateSecurityArea(SecurityAreaReqDTO.UPDATE update) {

        this.name = update.getName();
        this.description = update.getDescription();
        this.location = update.getLocation();
    }
}
