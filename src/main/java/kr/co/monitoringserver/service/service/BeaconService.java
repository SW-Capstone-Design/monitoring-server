package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserBeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import kr.co.monitoringserver.service.dtos.response.BeaconResDTO;
import kr.co.monitoringserver.service.mappers.BeaconMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BeaconService {

    private final BeaconRepository beaconRepository;

    private final UserBeaconRepository userBeaconRepository;

    private final UserRepository userRepository;

    private final BeaconMapper beaconMapper;

//    /**
//     * createBeacon : Beacon 정보를 생성한다.
//     */
//    @Transactional
//    public void createBeacon(BeaconReqDTO.SERVER beaconReqDTO){
//
//        Beacon beacon = Beacon.builder()
//                .uuid(beaconReqDTO.getUuid())
//                .beaconName(beaconReqDTO.getBeaconName())
//                .major(beaconReqDTO.getMajor())
//                .minor(beaconReqDTO.getMinor())
//                .beaconRole(beaconReqDTO.getBeaconRole())
//                .build();
//
//        beaconRepository.save(beacon);
//    }
//
//    /**
//     * createDistance : tbl_user_beacon에 RSSI 저장
//     */
//    @Transactional
//    public void createDistance(Long userId, BeaconReqDTO.CLIENT beaconReqDTO){
//        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeacon().getBeaconId())
//                .orElseThrow(()->{
//                    return new IllegalArgumentException("비콘 찾기 실패");
//                });
//        beacon.setBattery(beaconReqDTO.getBattery());
//
//        User user = userRepository.findByUserId(userId)
//                .orElseThrow(()->{
//                    return new IllegalArgumentException("유저 찾기 실패");
//                });
//
//        UserBeacon userBeacon = UserBeacon.builder()
//                .rssi(beaconReqDTO.getRssi())
//                .beacon(beaconReqDTO.getBeacon())
//                .user(user)
//                .build();
//
//        userBeaconRepository.save(userBeacon);
//    }
//
//    /**
//     * updateDistance : tbl_user_beacon에 RSSI 저장
//     */
//    @Transactional
//    public void updateDistance(Long userId, Long userBeaconId,BeaconReqDTO.CLIENT beaconReqDTO){
//
//        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeacon().getBeaconId())
//                .orElseThrow(()->{
//                    return new IllegalArgumentException("비콘 찾기 실패");
//                });
//        beacon.setBattery(beaconReqDTO.getBattery());
//
//        User user = userRepository.findByUserId(userId)
//                .orElseThrow(()->{
//                    return new IllegalArgumentException("유저 찾기 실패");
//                });
//
//        UserBeacon userBeacon = userBeaconRepository.findByUserBeaconId(userBeaconId)
//                    .orElseThrow(()->{
//                        return new IllegalArgumentException("비콘 찾기 실패");
//                    });
//        userBeacon.setBeacon(beaconReqDTO.getBeacon());
//        userBeacon.setUser(user);
//        userBeacon.setRssi(beaconReqDTO.getRssi());
//    }
//
//    @Transactional
//    public void deleteDistance(Long userId){
//        List<UserBeacon> userBeacon = userBeaconRepository.findByUser_UserId(userId);
//
//        userBeaconRepository.deleteAllInBatch(userBeacon);
//    }
//
//    /**
//     * list : Beacon 목록을 조회하여 Page 객체로 반환한다.
//     */
//    public Page<Beacon> list(Pageable pageable) {
//
//        return beaconRepository.findAll(pageable);
//    }
//
//    /**
//     * beaocnList : 모바일 클라이언트에 넘겨주기 위해 Beacon 목록을 조회하여 List 객체로 반환한다.
//     */
//    public List<BeaconResDTO> beaconList() {
//        List<Beacon> all = beaconRepository.findAll();
//        List<BeaconResDTO> collect = new ArrayList<>();
//
//        for(Beacon beacon : all){
//            BeaconResDTO beaconResDTO = BeaconResDTO.builder()
//                    .beaconId(beacon.getBeaconId())
//                    .uuid(beacon.getUuid())
//                    .major(beacon.getMajor())
//                    .minor(beacon.getMinor())
//                    .beaconRole(beacon.getBeaconRole())
//                    .build();
//
//            collect.add(beaconResDTO);
//        }
//
//        return collect;
//    }
//
//    /**
//     * detail : beaconId 변수를 인자로 전달하여 해당 Beacon을 조회한다.
//     */
////    public Beacon detail(Long beaconId){
////
////        return beaconRepository.findByBeaconId(beaconId);
////    }
//
//    /**
//     * updateBeacon : Beacon의 UUID, BeaconName, Major, Minor 정보를 수정한다.
//     */
//    @Transactional
//    public void updateBeacon(BeaconReqDTO.SERVER beaconReqDTO){
//        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeaconId())
//                .orElseThrow(()->{
//            return new IllegalArgumentException("비콘 찾기 실패");
//        });
//        beacon.setUuid(beaconReqDTO.getUuid());
//        beacon.setMajor(beaconReqDTO.getMajor());
//        beacon.setMinor(beaconReqDTO.getMinor());
//        beacon.setBeaconName(beaconReqDTO.getBeaconName());
//        beacon.setBeaconRole(beaconReqDTO.getBeaconRole());
//    }
//
//    /**
//     * deleteBeacon : Beacon 정보를 삭제한다.
//     */
//    @Transactional
//    public void deleteBeacon(BeaconReqDTO.SERVER beaconReqDTO){
//        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeaconId())
//                .orElseThrow(()->{
//                    return new IllegalArgumentException("비콘 찾기 실패");
//                });
//
//        beaconRepository.delete(beacon);
//    }
//
//    /**
//     * calculateLocation : 삼변측량
//     * 테스트를 어떻게 하는 것이 좋을지
//     * userBeaconRepository.findByUser_UserId 한 결과가 3개 미만일 경우 어떻게 처리할 지 고민됩니다.
//     * 칼만 필터도 적용해야할 것이고
//     * Quartz Scheduler를 이용해야할 것 같은데 해당 부분 연구가 필요합니다.
//     */
//    @Transactional
//    public void calculateLocation(Long userId) {
//        List<UserBeacon> userBeacon = userBeaconRepository.findByUser_UserId(userId);
//
//        List<Double> xArray = null;
//        List<Double> yArray = null;
//        List<Double> rArray = null;
//        int count = 0;
//        int n = 2;
//        int txPower = -59;
//
//        for (UserBeacon e : userBeacon){
//            xArray.add(userBeacon.get(count).getBeacon().getX()); // 비콘 x 좌표
//            yArray.add(userBeacon.get(count).getBeacon().getY()); // 비콘 y 좌표
//            rArray.add(Math.pow(10, txPower-userBeacon.get(count).getRssi()/(10*n))); // 3개의 비콘으로부터의 사용자까지의 직선거리
//            count++;
//            if(count == 3){
//                break;
//            }
//        }
//
//        // 3개의 비콘 x, y 좌표
//        double x1 = xArray.get(0);
//        double y1 = yArray.get(0);
//        double x2 = xArray.get(1);
//        double y2 = yArray.get(1);
//        double x3 = xArray.get(2);
//        double y3 = yArray.get(2);
//
//        // 3개의 비콘으로부터의 직선거리
//        double r1 = rArray.get(0);
//        double r2 = rArray.get(1);
//        double r3 = rArray.get(2);
//
//        double S = (Math.pow(x3, 2.) - Math.pow(x2, 2.)
//                + Math.pow(y3, 2.) - Math.pow(y2, 2.))
//                + Math.pow(r2, 2.) - Math.pow(r3, 2.) / 2.0;
//
//        double T = (Math.pow(x1, 2.) - Math.pow(x2, 2.)
//                + Math.pow(y1, 2.) - Math.pow(y2, 2.))
//                + Math.pow(r2, 2.) - Math.pow(r1, 2.) / 2.0;
//
//        // 사용자 위치
//        double y = ((T * (x2 - x3)) - (S * (x2 - x1))) / (((y1 - y2) * (x2 - x3)) - ((y3 - y2) * (x2 - x1)));
//        double x = ((y * (y1 - y2)) - T) / (x2 - x1);
//
//        System.out.println(x+" and "+y);
//    }







    /**
     * Check Beacon Battery Level And Send Notification Service
     * 해당 알림 버튼을 누를 경우,
     * QueryDSL 을 사용해 배터리 잔량이 20% 미만인 경우 전체 조회를 할 수 있게 구현
     */
    @Scheduled(fixedRate = 60000)
    public void checkBatteryStatusAndSendNotification() {

        List<Beacon> beacons = beaconRepository.findBeaconsByBatteryLessThan(20);

        for (Beacon beacon : beacons) {
            sendBatteryLowNotification(beacon);
        }
    }

    /**
     * Create Beacon And Beacon Location Service
     */
    @Transactional
    public void createBeaconInfoAndLocation(BeaconReqDTO.CREATE create) {

        Beacon beacon = beaconMapper.toBeaconEntity(create);

        beaconRepository.save(beacon);
    }

    /**
     * Get All Beacon And Beacon Location Service
     */
    public Page<BeaconResDTO.READ> getAllBeaconInfoAndLocation(Pageable pageable) {

        Page<Beacon> beaconPage = beaconRepository.findAll(pageable);

        List<BeaconResDTO.READ> readList = beaconPage.stream()
                .map(beaconMapper::toBeaconReadDto)
                .collect(Collectors.toList());

        return new PageImpl<>(readList, pageable, beaconPage.getTotalElements());
    }

    /**
     * Update Beacon And Beacon Location Service
     */
    @Transactional
    public void updateBeaconInfoAndLocation(Long beaconId, BeaconReqDTO.UPDATE update) {

        final Beacon beacon = beaconRepository.findById(beaconId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));

        beacon.updateBeacon(update);
    }


    // TODO  fetch PR, Save BeaconName, Battery
    // 비콘 배터리가 20% 미만일 경우 알림 설정
    private void sendBatteryLowNotification(Beacon beacon) {

        System.out.printf(
                "Beacon %s의 배터리 잔량이 20%% 미만입니다. 현재 잔량: %d%%\n",
                beacon.getBeaconName(), beacon.getBattery());
    }
}

