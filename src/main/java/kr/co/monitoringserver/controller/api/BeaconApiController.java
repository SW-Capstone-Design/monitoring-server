package kr.co.monitoringserver.controller.api;


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

    @PostMapping("/createBeacon")
    public void createBeacon(@RequestBody String data, BeaconReqDTO beaconReqDTO) {

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
            BeaconReqDTO dto = new BeaconReqDTO();

            beaconReqDTO.setUuid((String)signal.get("uuid"));
            beaconReqDTO.setMajor((String)signal.get("major"));
            beaconReqDTO.setMinor((String)signal.get("minor"));

            String rssi = (signal.get("rssi")).toString();
            Long u = Long.valueOf(rssi).longValue();
            beaconReqDTO.setRssi(u);

            beaconService.createBeacon(beaconReqDTO);
        }
    }
}
