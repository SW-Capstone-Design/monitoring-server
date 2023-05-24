package kr.co.monitoringserver.service.mappers;

import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import kr.co.monitoringserver.persistence.entity.user.User;
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
                .txPower(1)
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

    default UserBeacon toUserBeaconEntity(User user, Beacon beacon, Short rssi) {

        final UserBeacon userBeacon = UserBeacon.builder()
                .user(user)
                .beacon(beacon)
                .rssi(rssi)
                .build();

        return userBeacon;
    }

    @Mapping(source = "beacon.beaconName", target = "beaconName")
    @Mapping(source = "beacon.major", target = "major")
    @Mapping(source = "beacon.minor", target = "minor")
    @Mapping(source = "beacon.uuid", target = "uuid")
    @Mapping(source = "beacon.location", target = "location")
    @Mapping(source = "beacon.battery", target = "battery")
    @Mapping(source = "beacon.beaconRole", target = "beaconRole")
    @Mapping(source = "beacon.txPower", target = "txPower")
    BeaconResDTO.READ toBeaconReadDto(Beacon beacon);
}
