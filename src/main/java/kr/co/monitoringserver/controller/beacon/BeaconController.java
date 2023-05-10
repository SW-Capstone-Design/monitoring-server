package kr.co.monitoringserver.controller.beacon;

import kr.co.monitoringserver.service.service.BeaconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BeaconController {

    @Autowired
    private BeaconService beaconService;

    /**
     * beaconSendForm : 테스트용 메소드로, 더미 BeaconData를 전송한다.
     */
    @GetMapping("admin/beacon/data")
    public String beaconSendForm(){

        return "admin/beacon/create";
    }

    /**
     * beaconList : BeaconData list를 Select하여 조회가 가능하다.
     * 최대 비콘 6개이므로 한 페이지에 모두 표시 가능하다.
     */
    @GetMapping("admin/beacon/info")
    public String beaconList(Model model, @PageableDefault(size=6, sort="beaconId", direction = Sort.Direction.ASC) Pageable pageable){

        model.addAttribute("lists", beaconService.list(pageable));

        return "admin/beacon/list";
    }

    @GetMapping("/admin/beacon/info/{beacon_id}")
    public String beaconUpdateForm(Model model, @PathVariable(name = "beacon_id") Long beaconId){
        model.addAttribute("lists", beaconService.detail(beaconId));

        return "admin/beacon/updateForm";
    }
}
