package kr.co.monitoringserver.controller.beacon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeaconController {
    @GetMapping("/admin/beaconList")
    public String beaconList() {

        return "admin/beacon/beaconList";
    }
}
