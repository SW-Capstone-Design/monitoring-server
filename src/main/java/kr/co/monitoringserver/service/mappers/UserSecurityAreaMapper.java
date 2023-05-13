package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.securityArea.UserSecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.service.dtos.response.UserSecurityAreaResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface UserSecurityAreaMapper {

    // User, SecurityArea Entity -> SecurityAccessLog Entity
    @Mapping(source = "user", target = "user")
    @Mapping(source = "securityArea", target = "securityArea")
    @Mapping(source = "accessTime", target = "accessTime")
    UserSecurityArea toUserSecurityAreaEntity(User user, SecurityArea securityArea, LocalTime accessTime);

    // SecurityAccessLog Entity -> UserSecurityAreaResDTO.READ
    @Mapping(source = "securityArea.name", target = "securityAreaName")
    @Mapping(source = "securityArea.description", target = "securityAreaDescription")
    @Mapping(source = "securityArea.securityAreaLocation", target = "securityAreaLocation")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.roleType", target = "userRoleType")
    @Mapping(source = "user.department", target = "userDepartment")
    @Mapping(source = "accessTime", target = "accessTime")
    UserSecurityAreaResDTO.READ toUserSecurityAreaReadDto(UserSecurityArea userSecurityArea);
}
