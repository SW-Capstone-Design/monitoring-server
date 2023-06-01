package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.alert.WarningNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface WarningNotificationMapper {

    @Mapping(source = "time", target = "time")
    @Mapping(source = "content", target = "content")
    WarningNotification toWarningNotificationEntity(LocalTime time, String content);
}
