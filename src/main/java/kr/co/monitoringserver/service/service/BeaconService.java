package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.controller.api.AdminApiController;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.alert.IndexNotification;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.persistence.repository.IndexNotificationRepository;
import kr.co.monitoringserver.persistence.repository.UserBeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import kr.co.monitoringserver.service.dtos.response.BeaconResDTO;
import kr.co.monitoringserver.service.mappers.BeaconMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    private final IndexNotificationRepository indexNotificationRepository;


    /**
     * createDistance : tbl_user_beacon에 RSSI 저장
     */
    @Transactional
    public void createDistance(String identity, BeaconReqDTO.CLIENT beaconReqDTO){
        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeacon().getBeaconId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("비콘 찾기 실패");
                });
        beacon.setBattery(beaconReqDTO.getBattery());

        User user = userRepository.findByIdentity(identity)
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
    public void updateDistance(String identity, Long userBeaconId, BeaconReqDTO.CLIENT beaconReqDTO){

        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeacon().getBeaconId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("비콘 찾기 실패");
                });
        beacon.setBattery(beaconReqDTO.getBattery());

        User user = userRepository.findByIdentity(identity)
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
    public void deleteDistance(String identity){
        List<UserBeacon> userBeacon = userBeaconRepository.findByUser_Identity(identity);

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
                    .beaconRole(beacon.getBeaconRole())
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
     * Check Beacon Battery Level And Send Notification Service
     */
    @Scheduled(fixedRate = 60000)
    public void checkBatteryStatusAndSendNotification() {

        List<Beacon> beacons = beaconRepository.findBeaconsByBatteryLessThan(20);
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatedNow = now.format(formatter);

        for (Beacon beacon : beacons) {
            sendBatteryLowNotification(beacon);
            String lowBatteryBeacon = "Beacon : " + beacon.getBeaconName() + "의 배터리 잔량이 20% 미만입니다." +
                    " 현재 잔량 : " + beacon.getBattery().toString() + "%";

            JSONObject obj = new JSONObject();
            obj.put("text", "[" + formatedNow + "] " + lowBatteryBeacon);

            String eventFormatted = obj.toString();
            List<SseEmitter> emitters = AdminApiController.emitters;

            for (SseEmitter emitter : emitters) {
                try{
                    emitter.send(SseEmitter.event().name("latest").data(eventFormatted));
                } catch (IOException e) {
                    emitters.remove(emitter);
                }
            }

            IndexNotification indexNotification = indexNotificationRepository.findByIndexAlertContent(lowBatteryBeacon);
            if(indexNotification == null){
                IndexNotification alert = new IndexNotification();
                alert.setIndexAlertContent(lowBatteryBeacon);
                indexNotificationRepository.save(alert);
            }
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

    /**
     * Delete Beacon And Beacon Location Service
     */
    @Transactional
    public void deleteBeaconInfoAndLocation(Long beaconId) {

        final Beacon beacon = beaconRepository.findById(beaconId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));

        beaconRepository.delete(beacon);
    }


    // TODO  fetch PR, Save BeaconName, Battery
    // 비콘 배터리가 20% 미만일 경우 알림 설정
    private void sendBatteryLowNotification(Beacon beacon) {

        System.out.printf(
                "Beacon %s의 배터리 잔량이 20%% 미만입니다. 현재 잔량: %d%%\n",
                beacon.getBeaconName(), beacon.getBattery());
    }
}
