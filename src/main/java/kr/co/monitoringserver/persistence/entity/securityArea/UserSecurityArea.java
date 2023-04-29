package kr.co.monitoringserver.persistence.entity.securityArea;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.entity.BaseEntity;
import kr.co.monitoringserver.persistence.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_user_security_area")
@AttributeOverride(
        name = "id",
        column = @Column(name = "user_security_area_id", length = 4))
public class UserSecurityArea extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "security_area_id")
    private SecurityArea securityArea;


    @Builder
    private UserSecurityArea(User user,
                             SecurityArea securityArea) {

        this.user = user;
        this.securityArea = securityArea;
    }
}
