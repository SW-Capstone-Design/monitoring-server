package kr.co.monitoringserver.controller.api;

import kr.co.monitoringserver.infra.global.model.ResponseFormat;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserBeaconRepository;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import kr.co.monitoringserver.service.dtos.response.BeaconResDTO;
import kr.co.monitoringserver.service.dtos.response.ResponseDto;
import kr.co.monitoringserver.service.service.BeaconService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BeaconApiController {

    @Autowired
    private final BeaconService beaconService;

    @Autowired
    private final UserBeaconRepository userBeaconRepository;

    @Autowired
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
    @PostMapping("/auth/receiveBeacon/{user_id}")
    public void receiveBeacon(@PathVariable(name = "user_id") Long userId, @RequestBody String data, BeaconReqDTO.CLIENT beaconReqDTO) {
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

            UserBeacon userBeacon = userBeaconRepository.findByBeacon_BeaconId(bi);
            if (userBeacon == null) {
                beaconService.createDistance(userId, beaconReqDTO);
            } else {
                beaconService.updateDistance(userId, userBeacon.getUserBeaconId(), beaconReqDTO);
            }
        }
    }

    /**
     * deleteBeaconConnection : 모바일 클라이언트에서 연결을 끊을 시 tbl_user_beacon에서 해당 유저의 Data 전부 삭제
     */
    @DeleteMapping("/auth/deleteBeaconConnection/{user_id}")
    public void deleteBeaconConnection(@PathVariable(name = "user_id")Long userId) {

        beaconService.deleteDistance(userId);
    }

    
    /**
     * createBeacon : Beacon Data를 Create한다.
     */
    @PostMapping("/admin/createBeacon")
    public ResponseDto<?> createBeacon(@RequestBody BeaconReqDTO.SERVER beaconReqDTO) {

        beaconService.createBeacon(beaconReqDTO);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * updateBeacon : Beacon Data를 Update한다.
     */
    @PutMapping("/admin/beacon/info/update")
    public void updateBeacon(@RequestBody BeaconReqDTO.SERVER beaconReqDTO){

        beaconService.updateBeacon(beaconReqDTO);

    }

    /**
     * deleteBeacon : Beacon Data를 DELETE 한다.
     */
    @DeleteMapping("/admin/beacon/info/delete")
    public void deleteBeacon(@RequestBody BeaconReqDTO.SERVER beaconReqDTO){

        beaconService.deleteBeacon(beaconReqDTO);

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
     * Create And Save Beacon And Beacon Location Controller
     */
    @PostMapping("/api/v1/beacon/{user_identity}")
    public ResponseFormat<Void> createBeaconAndLocation(@PathVariable(name = "user_identity") String userIdentity,
                                                        @RequestBody @Validated BeaconReqDTO.CREATE create) {

        beaconService.createBeaconAndLocation(userIdentity, create);

        return ResponseFormat.successMessage(
                ResponseStatus.SUCCESS_CREATED,
                "해당 비콘의 정보와 위치 정보가 성공적으로 생성되었습니다"
        );
    }
}
