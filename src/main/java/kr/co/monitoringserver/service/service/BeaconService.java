package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserBeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
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

    @Autowired
    private final UserBeaconRepository userBeaconRepository;

    @Autowired
    private final UserRepository userRepository;

    /**
     * createBeacon : Beacon 정보를 생성한다.
     */
    @Transactional
    public void createBeacon(BeaconReqDTO.SERVER beaconReqDTO){

        Beacon beacon = Beacon.builder()
                .uuid(beaconReqDTO.getUuid())
                .beaconName(beaconReqDTO.getBeaconName())
                .major(beaconReqDTO.getMajor())
                .minor(beaconReqDTO.getMinor())
                .build();

        beaconRepository.save(beacon);
    }

    /**
     * createDistance : tbl_user_beacon에 RSSI 저장
     */
    @Transactional
    public void createDistance(Long userId, BeaconReqDTO.CLIENT beaconReqDTO){
        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeacon().getBeaconId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("비콘 찾기 실패");
                });
        beacon.setBattery(beaconReqDTO.getBattery());

        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->{
                    return new IllegalArgumentException("유저 찾기 실패");
                });

        UserBeacon userBeacon = UserBeacon.builder()
                .rssi(beaconReqDTO.getRssi())
                .beacon(beaconReqDTO.getBeacon())
                .user(user)
                .build();

        userBeaconRepository.save(userBeacon);
    }

    /**
     * updateDistance : tbl_user_beacon에 RSSI 저장
     */
    @Transactional
    public void updateDistance(Long userId, Long userBeaconId,BeaconReqDTO.CLIENT beaconReqDTO){

        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeacon().getBeaconId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("비콘 찾기 실패");
                });
        beacon.setBattery(beaconReqDTO.getBattery());

        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->{
                    return new IllegalArgumentException("유저 찾기 실패");
                });

        UserBeacon userBeacon = userBeaconRepository.findByUserBeaconId(userBeaconId)
                    .orElseThrow(()->{
                        return new IllegalArgumentException("비콘 찾기 실패");
                    });
        userBeacon.setBeacon(beaconReqDTO.getBeacon());
        userBeacon.setUser(user);
        userBeacon.setRssi(beaconReqDTO.getRssi());
    }

    @Transactional
    public void deleteDistance(Long userId){
        List<UserBeacon> userBeacon = userBeaconRepository.findByUser_UserId(userId);

        userBeaconRepository.deleteAllInBatch(userBeacon);
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
    public void updateBeacon(BeaconReqDTO.SERVER beaconReqDTO){
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
    public void deleteBeacon(BeaconReqDTO.SERVER beaconReqDTO){
        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeaconId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("비콘 찾기 실패");
                });

        beaconRepository.delete(beacon);
    }

}

