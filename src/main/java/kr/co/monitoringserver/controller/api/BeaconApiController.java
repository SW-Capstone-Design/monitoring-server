package kr.co.monitoringserver.controller.api;


import kr.co.monitoringserver.persistence.entity.Beacon;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import kr.co.monitoringserver.service.service.BeaconService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BeaconApiController {

    @Autowired
    private BeaconService beaconService;


    /**
     * receiveBeacon : Beacon Data를 INSERT 또는 UPDATE 합니다.
     * DB에 해당 UUID가 있다면 Update, 없다면 Insert 합니다.
     */
    @PostMapping("/receiveBeacon")
    public void receiveBeacon(@RequestBody String data, BeaconReqDTO beaconReqDTO) {

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

            beaconReqDTO.setUuid((String)signal.get("uuid"));
            beaconReqDTO.setMajor((String)signal.get("major"));
            beaconReqDTO.setMinor((String)signal.get("minor"));

            String rssi = (signal.get("rssi")).toString();
            Long r = Long.valueOf(rssi).longValue();
            beaconReqDTO.setRssi(r);

            Beacon beacon = beaconService.findBeacon((String)signal.get("uuid"));
            if (beacon == null) {
                beaconService.createBeacon(beaconReqDTO);
            } else {
                beaconService.updateBeacon(beaconReqDTO);
            }
        }
    }

    /**
     * receiveBeacon : Beacon Data를 DELETE 합니다.
     * <a> 태그의 경우 get 요청을 보내므로 GetMapping을 이용한 DB Data 삭제를 수행합니다.
     */
    @GetMapping("/admin/beacon/info/{beacon_uuid}")
    public void deleteBeacon(@PathVariable(name = "beacon_uuid") String uuid){

        beaconService.deleteBeacon(uuid);

    }
}