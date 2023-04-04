package kr.co.monitoringserver.controller.api;

import kr.co.monitoringserver.service.service.AttendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attend")
@RequiredArgsConstructor
public class AttendController {

    private final AttendService attendService;
}
