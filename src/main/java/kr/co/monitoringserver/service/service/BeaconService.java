package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.entity.Beacon;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BeaconService {

    @Autowired
    private final BeaconRepository beaconRepository;

    /**
     * createBeacon : Beacon 정보를 생성한다.
     */
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

    /**
     * list : Beacon 목록을 조회하여 Page 객체로 반환한다.
     */
    public Page<Beacon> list(Pageable pageable) {

        return beaconRepository.findAll(pageable);
    }

    /**
     * findBeacon : uuid 변수를 인자로 전달하여 해당 Beacon을 조회한다.
     */
    public Beacon findBeacon(String uuid){

        return beaconRepository.findByUuid(uuid);
    }

    /**
     * updateBeacon : Beacon의 Major, Minor, RSSI 정보를 수정한다.
     */
    @Transactional
    public void updateBeacon(BeaconReqDTO beaconReqDTO){
        Beacon beacon = beaconRepository.findOptionalByUuid(beaconReqDTO.getUuid())
                .orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });
        beacon.setMajor(beaconReqDTO.getMajor());
        beacon.setMinor(beaconReqDTO.getMinor());
        beacon.setRssi(beaconReqDTO.getRssi());
    }

    /**
     * deleteBeacon : Beacon 정보를 삭제한다.
     */
    @Transactional
    public void deleteBeacon(String uuid){
        Beacon beacon = beaconRepository.findOptionalByUuid(uuid)
                .orElseThrow(()->{
                    return new IllegalArgumentException("회원 찾기 실패");
                });

        beaconRepository.delete(beacon);
    }

}

