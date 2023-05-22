package kr.co.monitoringserver.service.dtos.response;

import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.service.enums.RoleType;
import lombok.*;

import java.time.LocalTime;

public class UserSecurityAreaResDTO {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class READ {

        private String securityAreaName;

        private String securityAreaDescription;

        private Location securityAreaLocation;

        private String userName;

        private RoleType userRoleType;

        private String userDepartment;

        private LocalTime accessTime;
    }
}
