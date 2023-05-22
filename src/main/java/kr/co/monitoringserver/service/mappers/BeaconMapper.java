package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import kr.co.monitoringserver.service.dtos.response.BeaconResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeaconMapper {

    // BeaconReqDTO.CREATE -> Beacon Entity
    default Beacon toBeaconEntity(BeaconReqDTO.CREATE create) {

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

    @Mapping(source = "beacon.beaconName", target = "beaconName")
    @Mapping(source = "beacon.major", target = "major")
    @Mapping(source = "beacon.minor", target = "minor")
    @Mapping(source = "beacon.uuid", target = "uuid")
    @Mapping(source = "beacon.location", target = "location")
    @Mapping(source = "beacon.battery", target = "battery")
    @Mapping(source = "beacon.beaconRole", target = "beaconRole")
    BeaconResDTO.READ toBeaconReadDto(Beacon beacon);
}
