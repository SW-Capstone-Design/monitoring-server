package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.service.dtos.request.SecurityAreaReqDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SecurityAreaMapper {

    @Mapping(source = "create.name", target = "name")
    @Mapping(source = "create.description", target = "description")
    @Mapping(source = "create.location.latitude", target = "location.latitude")
    @Mapping(source = "create.location.longitude", target = "location.longitude")
    SecurityArea toSecurityAreaEntity(SecurityAreaReqDTO.CREATE create);

    @Mapping(source = "securityArea.name", target = "name")
    @Mapping(source = "securityArea.description", target = "description")
    @Mapping(source = "securityArea.location.latitude", target = "location.latitude")
    @Mapping(source = "securityArea.location.longitude", target = "location.longitude")
    SecurityAreaResDTO.READ toSecurityAreaReadDto(SecurityArea securityArea);
}
