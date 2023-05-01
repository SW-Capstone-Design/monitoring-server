package kr.co.monitoringserver.controller.beacon;

import kr.co.monitoringserver.service.service.BeaconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeaconController {

    @Autowired
    private BeaconService beaconService;

    @GetMapping("admin/beacon/data")
    public String beaconSendForm(){

        return "admin/beacon/create";
    }

    @GetMapping("admin/beacon/info")
    public String beaconList(Model model, @PageableDefault(size=6, sort="uuid", direction = Sort.Direction.ASC) Pageable pageable){

        model.addAttribute("lists", beaconService.list(pageable));

        return "admin/beacon/list";
    }
}
