package kr.co.monitoringserver.controller.api;

import kr.co.monitoringserver.infra.global.model.ResponseFormat;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserBeaconRepository;
import kr.co.monitoringserver.service.dtos.request.beacon.BeaconReqDTO;
import kr.co.monitoringserver.service.dtos.response.BeaconResDTO;
import kr.co.monitoringserver.service.service.beacon.BeaconService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class BeaconApiController {

    private final BeaconService beaconService;

    private final UserBeaconRepository userBeaconRepository;

    private final BeaconRepository beaconRepository;

    /**
     * transferBeacon : 서버의 Beacon Data를 모바일 클라이언트로 응답한다.
     */
    @GetMapping("/auth/transferBeacon")
    public List<BeaconResDTO> transferBeacon() {

        return beaconService.beaconList();
    }

    /**
     * receiveBeacon : 모바일 클라이언트에서 서버로 비콘과 사용자 간의 거리 정보를 보낸다.
     */
    @PostMapping("/auth/receiveBeacon")
    public void receiveBeacon(@RequestBody String data, BeaconReqDTO.CLIENT beaconReqDTO, Principal principal) {
        JSONParser jsonParser = new JSONParser();
        JSONArray insertParam = null;
        try {
            JSONObject signals = (JSONObject) jsonParser.parse(data);
            String temp = signals.get("signals").toString();
            insertParam = (JSONArray) jsonParser.parse(temp);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(int i=0; i<insertParam.size(); i++){

            JSONObject signal = (JSONObject) insertParam.get(i);

            String beaconId = (signal.get("beaconId")).toString();
            Long bi = Long.valueOf(beaconId).longValue();
            Beacon beacon = beaconRepository.findOptionalByBeaconId(bi)
                    .orElseThrow(()->{
                        return new IllegalArgumentException("비콘 찾기 실패");
                    });
            beaconReqDTO.setBeacon(beacon);

            String rssi = (signal.get("rssi")).toString();
            Short r = Short.valueOf(rssi).shortValue();
            beaconReqDTO.setRssi(r);

            String battery = (signal.get("battery")).toString();
            Short b = Short.valueOf(battery).shortValue();
            beaconReqDTO.setBattery(b);

            UserBeacon userBeacon = userBeaconRepository.findByBeacon_BeaconIdAndUser_UserId(bi, principal.getName());

            if (userBeacon == null) {
                beaconService.createDistance(principal.getName(), beaconReqDTO);
            } else {
                beaconService.updateDistance(principal.getName(), userBeacon.getUserBeaconId(), beaconReqDTO);
            }
        }
    }

    /**
     * deleteBeaconConnection : 모바일 클라이언트에서 연결을 끊을 시 tbl_user_beacon에서 해당 유저의 Data 전부 삭제
     */
    @DeleteMapping("/auth/deleteBeaconConnection")
    public void deleteBeaconConnection(Principal principal) {

        beaconService.deleteDistance(principal.getName());
    }




    /**
     * Check Beacon Battery Level And Send Notification Controller
     */
    @GetMapping("/api/v1/beacon/battery")
    public ResponseFormat<Void> checkBatteryStatusAndSendNotification() {

        beaconService.checkBatteryStatusAndSendNotification();

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_EXECUTE,
                "비콘의 배터리 잔량 20% 미만입니다"
        );
    }

    /**
     * Create Beacon And Beacon Location Controller
     */
    @PostMapping("/api/v1/beacon")
    public ResponseFormat<Void> createBeaconInfoAndLocation(@RequestBody @Validated BeaconReqDTO.CREATE create) {

        beaconService.createBeaconInfoAndLocation(create);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_CREATED,
                "해당 비콘의 정보와 위치 정보가 성공적으로 생성되었습니다"
        );
    }

    /**
     * Create Users RSSI Info For Existing Beacon Controller
     */
    @PostMapping("/api/v1/beacon/users_rssi")
    public ResponseFormat<Void> createUsersRssiInfoForExistingBeacon(@RequestBody @Validated List<BeaconReqDTO.MAPPING> mappingList) {

        beaconService.createUsersRssiInfoForExistingBeacon(mappingList);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_CREATED,
                "해당 사용자의 RSSI 정보가 성공적으로 생성되었습니다"
        );
    }

    /**
     * Get All Beacon And Beacon Location Controller
     */
    @GetMapping("/api/v1/beacon")
    public ResponseFormat<Page<BeaconResDTO.READ>> getAllBeaconInfoAndLocation(@PageableDefault Pageable pageable) {

        return ResponseFormat.successData(
                ResponseStatus.SUCCESS_EXECUTE,
                beaconService.getAllBeaconInfoAndLocation(pageable)
        );
    }

    /**
     * Update Beacon And Beacon Location Controller
     */
    @PutMapping("/api/v1/beacon/{beacon_id}/{user_identity}")
    public ResponseFormat<Void> updateBeaconInfoAndLocation(@PathVariable(name = "beacon_id") Long beaconId,
                                                            @PathVariable(name = "user_identity") String userIdentity,
                                                            @RequestBody @Validated BeaconReqDTO.UPDATE update) {

        beaconService.updateBeaconInfoAndLocation(beaconId, userIdentity, update);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_EXECUTE,
                "해당 비콘의 정보와 위치 정보가 성공적으로 수정되었습니다"
        );
    }

    /**
     * Delete Beacon And Beacon Location Controller
     */
    @DeleteMapping("/api/v1/beacon/{beacon_id}")
    public ResponseFormat<Void> deleteBeaconInfoAndLocation(@PathVariable(name = "beacon_id") Long beaconId) {

        beaconService.deleteBeaconInfoAndLocation(beaconId);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_EXECUTE,
                "해당 비콘의 정보와 위치 정보가 성공적으로 삭제되었습니다"
        );
    }
}
