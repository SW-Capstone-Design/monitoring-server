package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.securityArea.UserSecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaResDTO;
import kr.co.monitoringserver.service.dtos.response.UserSecurityAreaResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface SecurityAreaMapper {

    default SecurityArea toSecurityAreaEntity(SecurityAreaReqDTO.CREATE create) {

        final SecurityArea securityArea = SecurityArea.builder()
                .name(create.getName())
                .description(create.getDescription())
                .build();

        if (create.getLowerLeft() != null && create.getUpperRight() != null) {

            final Location lowerLeft = Location.builder()
                    .x(create.getLowerLeft().getX())
                    .y(create.getLowerLeft().getY())
                    .build();

            final Location upperRight = Location.builder()
                    .x(create.getUpperRight().getX())
                    .y(create.getUpperRight().getY())
                    .build();

            securityArea.createSecurityAreaLocation(lowerLeft, upperRight);
        }

        return securityArea;
    }

    @Mapping(source = "securityArea.name", target = "name")
    @Mapping(source = "securityArea.description", target = "description")
    @Mapping(source = "securityArea.lowerLeft", target = "lowerLeft")
    @Mapping(source = "securityArea.upperRight", target = "upperRight")
    SecurityAreaResDTO.READ toSecurityAreaReadDto(SecurityArea securityArea);

    // ============UserSecurityArea============//

    @Mapping(source = "user", target = "user")
    @Mapping(source = "securityArea", target = "securityArea")
    @Mapping(source = "accessTime", target = "accessTime")
    UserSecurityArea toUserSecurityAreaEntity(User user, SecurityArea securityArea, LocalTime accessTime);

    @Mapping(source = "securityArea.name", target = "securityAreaName")
    @Mapping(source = "securityArea.description", target = "securityAreaDescription")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.roleType", target = "userRoleType")
    @Mapping(source = "user.department", target = "userDepartment")
    @Mapping(source = "accessTime", target = "accessTime")
    UserSecurityAreaResDTO.READ toUserSecurityAreaReadDto(UserSecurityArea userSecurityArea);
}
