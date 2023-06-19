package kr.co.monitoringserver.service.service.securityArea;

import kr.co.monitoringserver.controller.api.AdminApiController;
import kr.co.monitoringserver.infra.global.exception.BadRequestException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.alert.IndexNotification;
import kr.co.monitoringserver.persistence.entity.securityArea.SecurityArea;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.IndexNotificationRepository;
import kr.co.monitoringserver.persistence.repository.SecurityAreaRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.securityArea.SecurityAreaLocationReqDTO;
import kr.co.monitoringserver.service.dtos.request.securityArea.SecurityAreaReqDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaLocationResDTO;
import kr.co.monitoringserver.service.dtos.response.SecurityAreaResDTO;
import kr.co.monitoringserver.service.enums.UserRoleType;
import kr.co.monitoringserver.service.mappers.SecurityAreaMapper;
import kr.co.monitoringserver.service.service.fcm.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityAreaService {

    private final SecurityAreaRepository securityAreaRepository;

    private final SecurityAreaMapper securityAreaMapper;

    private final UserRepository userRepository;

    private final SecurityAreaLocationService securityAreaLocationService;

    private final IndexNotificationRepository indexNotificationRepository;

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    /**
     * Create Security Area Service
     */
    @Transactional
    public void createSecurityArea(Principal principal, SecurityAreaReqDTO.CREATE create) {

        checkUserAuthorization(principal.getName());

        SecurityArea securityArea = securityAreaMapper.toSecurityAreaEntity(create);

        securityAreaRepository.save(securityArea);
    }

    /**
     * Get Security Area By id Service
     */
    public Page<SecurityAreaResDTO.READ> getSecurityAreaById(Principal principal, Long securityAreaId, Pageable pageable) {

        checkUserAuthorization(principal.getName());

        Page<SecurityArea> securityAreaPage = securityAreaRepository.findById(securityAreaId, pageable);

        return securityAreaPage.map(securityAreaMapper::toSecurityAreaReadDto);
    }


    /**
     * securityAreaDetail : SecurityAreaId로 조회하여 엔티티 타입 객체로 반환한다.
     */
    public SecurityArea securityAreaDetail(Long securityAreaId){

        return securityAreaRepository.findById(securityAreaId)
                .orElseThrow(()->{
                    return new IllegalArgumentException("보안구역 조회 실패 : 해당 보안구역 아이디를 찾을 수 없습니다.");
                });
    }

    /**
     * getSecurityArea : SecurityArea 의 모든 정보를 Select 하여 Page 를 반환한다.
     */
    public Page<SecurityArea> getSecurityArea(Pageable pageable) {

        return securityAreaRepository.findAll(pageable);
    }


    /**
     * Update Security Area Service
     */
    @Transactional
    public void updateSecurityArea(Principal principal, Long securityAreaId, SecurityAreaReqDTO.UPDATE update) {

        checkUserAuthorization(principal.getName());

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_SECURITY_AREA));

        securityArea.updateSecurityArea(update);
    }

    /**
     * Delete Security Area Service
     */
    @Transactional
    public void deleteSecurityArea(Principal principal, Long securityAreaId) {

        checkUserAuthorization(principal.getName());

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_SECURITY_AREA));

        securityAreaRepository.delete(securityArea);
    }

    /**
     * Handle User Access To SecurityArea Service
     */
    @Transactional
    public boolean handleUserAccessToSecurityArea(SecurityAreaLocationReqDTO.CREATE create) {

        // 사용자 인가 권한 여부 확인
        boolean isAuthorization = checkUserAuthorization(create.getUserIdentity());

        final User user = userRepository.findByIdentity(create.getUserIdentity())
                .orElseThrow(BadRequestException::new);

        final SecurityArea securityArea = securityAreaRepository.findById(create.getSecurityAreaId())
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_SECURITY_AREA));

        // 사용자가 보안구역 내에 있는지 확인 : 사용자-보안구역 위치 서비스 호출
        boolean isInsideSecurityArea = securityAreaLocationService.isUserInsideSecurityArea(user, securityArea);

        // 사용자가 보안구역 내에 있을 경우, 보안구역 출입 기록 정보를 생성 : 사용자-보안구역 위치 서비스 호출
        if (isInsideSecurityArea) {
            securityAreaLocationService.createSecurityAreaAccessLog(isAuthorization, user, securityArea);

            // SSE
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedNow = now.format(formatter);

            String securityAreaAccessLog = "Warning : " + user.getName().toString()
                    + "이(가)" + securityArea.getName() + "에 접근했습니다.";

            JSONObject obj = new JSONObject();
            obj.put("text", "[" + formattedNow + "] " + securityAreaAccessLog);

            String eventFormatted = obj.toString();
            List<SseEmitter> emitters = AdminApiController.emitters;

            for (SseEmitter emitter : emitters) {
                try{
                    emitter.send(SseEmitter.event().name("latest").data(eventFormatted));
                } catch (IOException e) {
                    emitters.remove(emitter);
                }
            }

            // FCM
            try {
                firebaseCloudMessageService.sendMessageTo(
                    user.getDeviceToken(),
                    "[팀투] Warning : 보안구역 접근 감지",
                    "보안구역에 접근했습니다. 관계자외 출입금지구역입니다.");
            } catch(IOException e) {
                System.out.println("FCM : 예외상황 발생");
            }

            // 관리자페이지 알림
            IndexNotification alert = new IndexNotification();
            alert.setIndexAlertContent(securityAreaAccessLog);
            indexNotificationRepository.save(alert);
        }
        return isAuthorization;
    }

    /**
     * Get User Security Area Access Logs Service
     */
    public Page<SecurityAreaLocationResDTO.READ> getUserSecurityAreaAccessLogs(Principal principal, Long securityAreaId, Pageable pageable) {

        final User user = userRepository.findByIdentity(principal.getName())
                .orElseThrow(BadRequestException::new);

        final SecurityArea securityArea = securityAreaRepository.findById(securityAreaId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_SECURITY_AREA));

        return securityAreaLocationService.getSecurityAccessLogByArea(user, securityArea, pageable);
    }



    // 사용자 인가 권한 여부 확인
    private boolean checkUserAuthorization(String userIdentity) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        if (user.getUserRoleType().equals(UserRoleType.ADMIN)) {
            return true;
        } else {
            return false;
        }
    }
}
