package kr.co.monitoringserver.controller.beacon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeaconController {
    @GetMapping("admin/beaconData")
    public String beaconSendForm(){

        return "admin/beacon/create";
    }
}
