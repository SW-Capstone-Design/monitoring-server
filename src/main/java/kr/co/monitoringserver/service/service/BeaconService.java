package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.entity.Beacon;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import kr.co.monitoringserver.service.dtos.response.BeaconResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                .beaconName(beaconReqDTO.getBeaconName())
                .major(beaconReqDTO.getMajor())
                .minor(beaconReqDTO.getMinor())
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
     * beaocnList : 모바일 클라이언트에 넘겨주기 위해 Beacon 목록을 조회하여 List 객체로 반환한다.
     */
    public List<BeaconResDTO> beaconList() {
        List<Beacon> all = beaconRepository.findAll();
        List<BeaconResDTO> collect = new ArrayList<>();

        for(Beacon beacon : all){
            BeaconResDTO beaconResDTO = BeaconResDTO.builder()
                    .beaconId(beacon.getBeaconId())
                    .uuid(beacon.getUuid())
                    .major(beacon.getMajor())
                    .minor(beacon.getMinor())
                    .build();

            collect.add(beaconResDTO);
        }

        return collect;
    }

    /**
     * detail : beaconId 변수를 인자로 전달하여 해당 Beacon을 조회한다.
     */
    public Beacon detail(Long beaconId){

        return beaconRepository.findByBeaconId(beaconId);
    }

    /**
     * updateBeacon : Beacon의 UUID, BeaconName, Major, Minor 정보를 수정한다.
     */
    @Transactional
    public void updateBeacon(BeaconReqDTO beaconReqDTO){
        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeaconId())
                .orElseThrow(()->{
            return new IllegalArgumentException("비콘 찾기 실패");
        });
        beacon.setUuid(beaconReqDTO.getUuid());
        beacon.setMajor(beaconReqDTO.getMajor());
        beacon.setMinor(beaconReqDTO.getMinor());
        beacon.setBeaconName(beaconReqDTO.getBeaconName());
    }

    /**
     * deleteBeacon : Beacon 정보를 삭제한다.
     */
    @Transactional
    public void deleteBeacon(BeaconReqDTO beaconReqDTO){
        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeaconId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("비콘 찾기 실패");
                });

        beaconRepository.delete(beacon);
    }

}

