package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.persistence.repository.AttendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendService {

    private final AttendRepository attendRepository;
}
