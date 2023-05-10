package kr.co.monitoringserver.controller.api;


import kr.co.monitoringserver.persistence.entity.Beacon;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BeaconApiController {

    @Autowired
    private BeaconService beaconService;

    /**
     * transferBeacon : 모바일 클라이언트에서 사용자 거리 정보 요청을 위해 서버의 Beacon Data를 보낸다.
     */
    @GetMapping("/transferBeacon")
    public List<BeaconResDTO> transferBeacon() {

        return beaconService.beaconList();
    }

    /**
     * createBeacon : Beacon Data를 Create한다.
     */
    @PostMapping("/admin/createBeacon")
    public ResponseDto<?> createBeacon(@RequestBody BeaconReqDTO beaconReqDTO) {

        beaconService.createBeacon(beaconReqDTO);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * updateBeacon : Beacon Data를 Update한다.
     */
    @PutMapping("/admin/beacon/info/update")
    public void updateBeacon(@RequestBody BeaconReqDTO beaconReqDTO){

        beaconService.updateBeacon(beaconReqDTO);

    }

    /**
     * deleteBeacon : Beacon Data를 DELETE 한다.
     */
    @DeleteMapping("/admin/beacon/info/delete")
    public void deleteBeacon(@RequestBody BeaconReqDTO beaconReqDTO){

        beaconService.deleteBeacon(beaconReqDTO);

    }
}


/*
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
        }*/
