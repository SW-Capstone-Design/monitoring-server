package kr.co.monitoringserver.service.service.beacon;

import kr.co.monitoringserver.controller.api.AdminApiController;
import kr.co.monitoringserver.infra.global.exception.BadRequestException;
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
import kr.co.monitoringserver.service.dtos.request.beacon.BeaconReqDTO;
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
import java.security.Principal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
     * beaconList : 모바일 클라이언트에 넘겨주기 위해 Beacon 목록을 조회하여 List 객체로 반환한다.
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
        String formattedNow = now.format(formatter);

        for (Beacon beacon : beacons) {
            sendBatteryLowNotification(beacon);

            // SSE
            String lowBatteryBeacon = "Beacon : " + beacon.getBeaconName() + "의 배터리 잔량이 20% 미만입니다." +
                    " 현재 잔량 : " + beacon.getBattery().toString() + "%";

            JSONObject obj = new JSONObject();
            obj.put("text", "[" + formattedNow + "] " + lowBatteryBeacon);

            String eventFormatted = obj.toString();
            List<SseEmitter> emitters = AdminApiController.emitters;

            for (SseEmitter emitter : emitters) {
                try{
                    emitter.send(SseEmitter.event().name("latest").data(eventFormatted));
                } catch (IOException e) {
                    emitters.remove(emitter);
                }
            }

            // 관리자페이지 알림
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
     * Create Users RSSI Info For Existing Beacon Service
     * 이미 존재하는 비콘에 기존 사용자들의 RSSI 정보를 생성 : 여러 사용자와 한 비콘 사이의 관계를 처리
     */
    @Transactional
    public void createUsersRssiInfoForExistingBeacon(List<BeaconReqDTO.MAPPING> mappingList) {

        for (BeaconReqDTO.MAPPING mapping : mappingList) {
            getOrCreateUserRssiInfo(mapping.getBeaconId(), mapping.getUserIdentity(), mapping.getRssi());
        }
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
    public void updateBeaconInfoAndLocation(Long beaconId, Principal principal, BeaconReqDTO.UPDATE update) {

        final Beacon beacon = beaconRepository.findById(beaconId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));

        beacon.updateBeaconInfoAndLocation(update);

        final User user = userRepository.findByIdentity(principal.getName())
                .orElseThrow(BadRequestException::new);

        final UserBeacon userBeacon = userBeaconRepository.findByUserAndBeacon(user, beacon)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));

        userBeacon.updateUserAndBeacon(user, beacon, update.getRssi());
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



    // 사용자와 비콘에 해당하는 RSSI 값을 확인하고 없으면 생성, 이미 있으면 업데이트
    private void getOrCreateUserRssiInfo(Long beaconId, String userIdentity, short rssi) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        final Beacon beacon = beaconRepository.findById(beaconId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));

        Optional<UserBeacon> userBeaconOptional = userBeaconRepository.findByUserAndBeacon(user, beacon);

        if (!userBeaconOptional.isPresent()) {

            UserBeacon newUserBeacon = beaconMapper.toUserBeaconEntity(user, beacon, rssi);

            userBeaconRepository.save(newUserBeacon);

        } else {

            UserBeacon existingUserBeacon = userBeaconOptional.get();

            existingUserBeacon.updateRssi(rssi);
        }
    }

    // 비콘 배터리가 20% 미만일 경우 알림 설정
    // TODO : 생성된 경고알림 정보를 경고알림 Repository 저장
    private void sendBatteryLowNotification(Beacon beacon) {

        System.out.printf(
                "Beacon %s의 배터리 잔량이 20%% 미만입니다. 현재 잔량: %d%%\n",
                beacon.getBeaconName(), beacon.getBattery());
    }
}
