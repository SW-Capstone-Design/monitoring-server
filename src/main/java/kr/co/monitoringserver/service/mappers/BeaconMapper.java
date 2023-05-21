package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeaconMapper {

    // BeaconReqDTO.CREATE -> Beacon Entity
    default Beacon toUserBeaconEntity(BeaconReqDTO.CREATE create) {

        final Beacon beacon = Beacon.builder()
                .beaconName(create.getBeaconName())
                .major(create.getMajor())
                .minor(create.getMinor())
                .beaconRole(create.getBeaconRole())
                .uuid(create.getUuid())
                .build();

        // 위치 정보가 있는 경우에만 Location 객체를 생성하고 비콘에 설정
        if (create.getLocation() != null) {
            final Location location = Location.builder()
                    .x(create.getLocation().getX())
                    .y(create.getLocation().getY())
                    .build();

            beacon.createBeaconLocation(location);
        }

        return beacon;
    }
}
