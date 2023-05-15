package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.error.enums.ErrorCode;
import kr.co.monitoringserver.infra.global.exception.BadRequestException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.TrilaterationFunction;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserBeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserRepository;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import kr.co.monitoringserver.service.dtos.response.BeaconResDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BeaconService {

    private final BeaconRepository beaconRepository;

    private final UserBeaconRepository userBeaconRepository;

    private final UserRepository userRepository;

    /**
     * createBeacon : Beacon 정보를 생성한다.
     */
    @Transactional
    public void createBeacon(BeaconReqDTO.SERVER beaconReqDTO){

        Beacon beacon = Beacon.builder()
                .uuid(beaconReqDTO.getUuid())
                .beaconName(beaconReqDTO.getBeaconName())
                .major(beaconReqDTO.getMajor())
                .minor(beaconReqDTO.getMinor())
                .beaconRole(beaconReqDTO.getBeaconRole())
                .build();

        beaconRepository.save(beacon);
    }

    /**
     * createDistance : tbl_user_beacon에 RSSI 저장
     */
    @Transactional
    public void createDistance(Long userId, BeaconReqDTO.CLIENT beaconReqDTO){
        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeacon().getBeaconId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("비콘 찾기 실패");
                });
        beacon.setBattery(beaconReqDTO.getBattery());

        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->{
                    return new IllegalArgumentException("유저 찾기 실패");
                });

        UserBeacon userBeacon = UserBeacon.builder()
                .rssi(beaconReqDTO.getRssi())
                .beacon(beaconReqDTO.getBeacon())
                .user(user)
                .build();

        userBeaconRepository.save(userBeacon);
    }

    /**
     * updateDistance : tbl_user_beacon에 RSSI 저장
     */
    @Transactional
    public void updateDistance(Long userId, Long userBeaconId,BeaconReqDTO.CLIENT beaconReqDTO){

        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeacon().getBeaconId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("비콘 찾기 실패");
                });
        beacon.setBattery(beaconReqDTO.getBattery());

        User user = userRepository.findByUserId(userId)
                .orElseThrow(()->{
                    return new IllegalArgumentException("유저 찾기 실패");
                });

        UserBeacon userBeacon = userBeaconRepository.findByUserBeaconId(userBeaconId)
                    .orElseThrow(()->{
                        return new IllegalArgumentException("비콘 찾기 실패");
                    });
        userBeacon.setBeacon(beaconReqDTO.getBeacon());
        userBeacon.setUser(user);
        userBeacon.setRssi(beaconReqDTO.getRssi());
    }

    @Transactional
    public void deleteDistance(Long userId){
        List<UserBeacon> userBeacon = userBeaconRepository.findByUser_UserId(userId);

        userBeaconRepository.deleteAllInBatch(userBeacon);
    }

    /**
     * list : Beacon 목록을 조회하여 Page 객체로 반환한다.
     */
    public Page<Beacon> list(Pageable pageable) {

        return beaconRepository.findAll(pageable);
    }

    /**
     * beaocnList : 모바일 클라이언트에 넘겨주기 위해 Beacon 목록을 조회하여 List 객체로 반환한다.
     */
    public List<BeaconResDTO> beaconList() {
        List<Beacon> all = beaconRepository.findAll();
        List<BeaconResDTO> collect = new ArrayList<>();

        for(Beacon beacon : all){
            BeaconResDTO beaconResDTO = BeaconResDTO.builder()
                    .beaconId(beacon.getBeaconId())
                    .uuid(beacon.getUuid())
                    .major(beacon.getMajor())
                    .minor(beacon.getMinor())
                    .beaconRole(beacon.getBeaconRole())
                    .build();

            collect.add(beaconResDTO);
        }

        return collect;
    }

    /**
     * detail : beaconId 변수를 인자로 전달하여 해당 Beacon을 조회한다.
     */
//    public Beacon detail(Long beaconId){
//
//        return beaconRepository.findByBeaconId(beaconId);
//    }

    /**
     * updateBeacon : Beacon의 UUID, BeaconName, Major, Minor 정보를 수정한다.
     */
    @Transactional
    public void updateBeacon(BeaconReqDTO.SERVER beaconReqDTO){
        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeaconId())
                .orElseThrow(()->{
            return new IllegalArgumentException("비콘 찾기 실패");
        });
        beacon.setUuid(beaconReqDTO.getUuid());
        beacon.setMajor(beaconReqDTO.getMajor());
        beacon.setMinor(beaconReqDTO.getMinor());
        beacon.setBeaconName(beaconReqDTO.getBeaconName());
        beacon.setBeaconRole(beaconReqDTO.getBeaconRole());
    }

    /**
     * deleteBeacon : Beacon 정보를 삭제한다.
     */
    @Transactional
    public void deleteBeacon(BeaconReqDTO.SERVER beaconReqDTO){
        Beacon beacon = beaconRepository.findOptionalByBeaconId(beaconReqDTO.getBeaconId())
                .orElseThrow(()->{
                    return new IllegalArgumentException("비콘 찾기 실패");
                });

        beaconRepository.delete(beacon);
    }

    /**
     * calculateLocation : 삼변측량
     * 테스트를 어떻게 하는 것이 좋을지
     * userBeaconRepository.findByUser_UserId 한 결과가 3개 미만일 경우 어떻게 처리할 지 고민됩니다.
     * 칼만 필터도 적용해야할 것이고
     * Quartz Scheduler를 이용해야할 것 같은데 해당 부분 연구가 필요합니다.
     */
    @Transactional
    public void calculateLocation(Long userId) {
        List<UserBeacon> userBeacon = userBeaconRepository.findByUser_UserId(userId);

        List<Double> xArray = null;
        List<Double> yArray = null;
        List<Double> rArray = null;
        int count = 0;
        int n = 2;
        int txPower = -59;

        for (UserBeacon e : userBeacon){
            xArray.add(userBeacon.get(count).getBeacon().getX()); // 비콘 x 좌표
            yArray.add(userBeacon.get(count).getBeacon().getY()); // 비콘 y 좌표
            rArray.add(Math.pow(10, txPower-userBeacon.get(count).getRssi()/(10*n))); // 3개의 비콘으로부터의 사용자까지의 직선거리
            count++;
            if(count == 3){
                break;
            }
        }

        // 3개의 비콘 x, y 좌표
        double x1 = xArray.get(0);
        double y1 = yArray.get(0);
        double x2 = xArray.get(1);
        double y2 = yArray.get(1);
        double x3 = xArray.get(2);
        double y3 = yArray.get(2);
        
        // 3개의 비콘으로부터의 직선거리
        double r1 = rArray.get(0);
        double r2 = rArray.get(1);
        double r3 = rArray.get(2);
        
        double S = (Math.pow(x3, 2.) - Math.pow(x2, 2.)
                + Math.pow(y3, 2.) - Math.pow(y2, 2.))
                + Math.pow(r2, 2.) - Math.pow(r3, 2.) / 2.0;

        double T = (Math.pow(x1, 2.) - Math.pow(x2, 2.)
                + Math.pow(y1, 2.) - Math.pow(y2, 2.))
                + Math.pow(r2, 2.) - Math.pow(r1, 2.) / 2.0;
        
        // 사용자 위치
        double y = ((T * (x2 - x3)) - (S * (x2 - x1))) / (((y1 - y2) * (x2 - x3)) - ((y3 - y2) * (x2 - x1)));
        double x = ((y * (y1 - y2)) - T) / (x2 - x1);

        System.out.println(x+" and "+y);
    }







    /**
     * Check Beacon Battery Level And Send Notification Service
     */
    @Scheduled(fixedRate = 60000)
    public void checkBatteryStatusAndSendNotification() {

        List<Beacon> beacons = beaconRepository.findBeaconsByBatteryLessThan(20);

        for (Beacon beacon : beacons) {
            sendBatteryLowNotification(beacon);
        }
    }

    /**
     * Create Beacon And Beacon Location
     * 비콘 생성 시 비콘 정보와 비콘의 위치정보도 저장
     */
    @Transactional
    public void createBeaconAndLocation(String userIdentity, BeaconReqDTO.CREATE create) {

        final User user = userRepository.findByIdentity(userIdentity)
                .orElseThrow(BadRequestException::new);

        final Location location = Location.builder()
                .x(create.getLocationX().getX())
                .y(create.getLocationY().getY())
                .build();

        final Beacon beacon = Beacon.builder()
                .beaconName(create.getBeaconName())
                .major(create.getMajor())
                .minor(create.getMinor())
                .beaconRole(create.getBeaconRole())
                .uuid(create.getUuid())
                .location(location)
                .build();

        final UserBeacon userBeacon = UserBeacon.builder()
                .user(user)
                .beacon(beacon)
                .rssi(create.getRssi())
                .build();

        userBeaconRepository.save(userBeacon);
    }

    /**
     * Get All Beacons Service
     */
    public List<Beacon> getAllBeacons() {

        return beaconRepository.findAll();
    }

    /**
     * Get Find User Location By Trilateration
     */
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void updateAndSaveUserLocation(Long beaconId) {

        final Beacon beacon = beaconRepository.findById(beaconId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_BEACON));

        List<Long> beaconIds = Arrays.asList(1L, 2L, 3L);
        // 실제 전송 전력 값으로 변경해야 한다
        int[] txPowers = new int[]{123, 456, 789};

        Location location = determineUserLocationWithTrilateration(beaconIds, txPowers);

        beacon.updateUserLocation(location);
    }


    // 각 비콘의 위치와 거리를 도출한 후 삼변측량을 사용해 사용자 위치를 계산
    private Location determineUserLocationWithTrilateration(List<Long> beaconIds, int[] txPowers) {

        // 데이터베이스에서 모든 비콘 객체를 가져온다
        List<Beacon> beacons = getAllBeacons();

        // 비콘 위치 정보를 2차원 배열로 변환한다
        double[][] positions = getBeaconPositions(beacons);

        // 사용자와 각 비콘 사이의 거리를 계산한다
        double[] distances = getBeaconDistances(beaconIds, txPowers);

        // 삼변 측량 기법을 사용하여 사용자 위치를 계산 및 반환한다
        return findUserLocationUsingTrilateration(positions, distances);
    }

    // 비콘 객체 목록을 사용하여 각 비콘의 위치 정보를 2차원 배열로 추출 및 반환
    private double[][] getBeaconPositions(List<Beacon> beacons) {

        // 비콘 목록의 크기와 동일한 크기의 2차원 배열을 생성한다
        // 배열의 각 행은 하나의 비콘 위치를 나타내며, 두 개의 열은 X, Y 좌표를 나타낸다
        double[][] positions = new double[beacons.size()][2];

        for (int i = 0; i < beacons.size(); i++) {
            Beacon beacon = beacons.get(i);
            // 비콘 목록을 순회하면서, 각 비콘의 Location 객체를 가져온다
            Location location = beacon.getLocation();
            // 가져온 위치 정보를 사용하여 두 개의 좌표(X, Y)를 추출하여 positions 배열에 저장한다
            positions[i] = new double[]{location.getX(), location.getY()};
        }

        return positions;
    }

    // 비콘 ID 목록과 각 비콘의 전송전력 값을 사용하여 사용자와 각 비콘 사이의 거리를 계산
    private double[] getBeaconDistances(List<Long> beaconIds, int[] txPowers) {

        double[] distances = new double[beaconIds.size()];

        for (int i = 0; i < beaconIds.size(); i++) {
            Long beaconId = beaconIds.get(i);
            // 각 비콘 ID에 해당하는 userBeaconOpt 객체를 찾는다
            Optional<UserBeacon> userBeaconOpt = beaconRepository.findByBeaconId(beaconId);

            // userBeaconOpt 객체가 존재하면, 해당 객체가 받은 RSSI 값을 사용하여 거리를 계산 및 distance 배열에 저장한다
            if (userBeaconOpt.isPresent()) {
                UserBeacon userBeacon = userBeaconOpt.get();
                Short rssi = userBeacon.getRssi();
                // 공식 : distance = 10 ^ ( (txPower - RSSI) / 10 )
                distances[i] = Math.pow(10, (double) (txPowers[i] - rssi) / 10.0);
            }
        }

        return distances;
    }

    // 비콘 위치 정보와 사용자와 각 비콘 사이의 거리 정보를 통해 삼변 측량 기법을 사용하여 사용자 위치를 계산
    private Location findUserLocationUsingTrilateration(double[][] positions, double[] distances) {

        // TrilaterationFunction 객체를 생성 및 비콘 위치와 거리 정보를 전달한다
        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);

        // LeastSquaresOptimizer 클래스의 인스턴스 생성
        LeastSquaresOptimizer leastSquaresOptimizer = new LevenbergMarquardtOptimizer();

        // LeastSquaresOptimizer.Optimum 호출하여 최적화 작업을 수행
        // 이 과정에서 최소제곱법을 사용하여 삼변 측량 기법으로 사용자 위치를 계산한다
        LeastSquaresOptimizer.Optimum optimum = leastSquaresOptimizer.optimize(
                        new LeastSquaresBuilder()
                                .start(new double[]{0, 0})
                                .model(trilaterationFunction)
                                .target(distances)
                                .weight(new DiagonalMatrix(distances))
                                .build());

        // 계산된 사용자 위치를 double 배열로 추출한다
        double[] calculatedLocation = optimum.getPoint().toArray();

        return new Location(calculatedLocation[0],calculatedLocation[1]);
    }

    // 비콘 배터리가 20% 미만일 경우 알림 설정
    private void sendBatteryLowNotification(Beacon beacon) {

        System.out.printf(
                "Beacon %s의 배터리 잔량이 20%% 미만입니다. 현재 잔량: %d%%\n",
                beacon.getBeaconName(), beacon.getBattery());
    }
}

