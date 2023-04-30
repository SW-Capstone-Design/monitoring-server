package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.entity.Beacon;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BeaconService {

    @Autowired
    private final BeaconRepository beaconRepository;

    @Transactional
    public void createBeacon(BeaconReqDTO beaconReqDTO){

        Beacon beacon = Beacon.builder()
                .uuid(beaconReqDTO.getUuid())
                .major(beaconReqDTO.getMajor())
                .minor(beaconReqDTO.getMinor())
                .rssi(beaconReqDTO.getRssi())
                .build();

        beaconRepository.save(beacon);
    }
}
