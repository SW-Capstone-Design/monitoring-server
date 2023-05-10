package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.securityArea.SecurityAccessLog;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface SecurityAccessLogMapper {

    // User, SecurityArea Entity -> SecurityAccessLog Entity
    @Mapping(source = "user", target = "user")
    @Mapping(source = "securityArea", target = "securityArea")
    @Mapping(source = "accessTime", target = "accessTime")
    SecurityAccessLog toSecurityAccessLogEntity(User user, SecurityArea securityArea, LocalTime accessTime);
}
