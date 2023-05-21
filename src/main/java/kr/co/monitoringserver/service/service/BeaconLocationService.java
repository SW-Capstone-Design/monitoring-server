package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.exception.InvalidInputException;
import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.TrilaterationFunction;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.service.dtos.request.BeaconLocationReqDTO;
import kr.co.monitoringserver.service.dtos.request.BeaconReqDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeaconLocationService {

    private final BeaconRepository beaconRepository;

    public Location createOrUpdateBeaconLocation(List<BeaconLocationReqDTO.LOCATION> locationList, Location location) {

        if (location != null) {
            return location;
        }

        List<Long> beaconIds = new ArrayList<>();
        double[] distances = new double[locationList.size()];
        int i = 0;

        for (BeaconLocationReqDTO.LOCATION locations : locationList) {
            beaconIds.add(locations.getBeaconId());
            distances[i++] = locations.getDistance();
        }

        // 삼변 측량 알고리즘을 이용하여 비콘의 위치를 설정
        return determineUserLocationWithTrilateration(beaconIds, distances);
    }

    // 비콘의 위치 정보를 생성
    public void createBeaconLocation(BeaconReqDTO.CREATE create, Beacon beacon) {

        Location location = createOrUpdateBeaconLocation(create.getLocationList(), create.getLocation());

        if (location != null) {
            beacon.createBeaconLocation(location);
        } else {
            throw new InvalidInputException();
        }
    }

    public void updateBeaconLocation(BeaconReqDTO.UPDATE update, Beacon beacon) {

        Location location = createOrUpdateBeaconLocation(update.getLocationList(), update.getLocation());

        if (location != null) {
            beacon.createBeaconLocation(location);
        } else {
            throw new InvalidInputException();
        }
    }

    // 각 비콘의 위치와 거리를 도출한 후 삼변측량을 사용해 사용자 위치를 계산
    public Location determineUserLocationWithTrilateration(List<Long> beaconIds, double[] distances) {

        // 데이터베이스에서 모든 비콘 객체를 가져온다
        List<Beacon> beacons = getAllBeacons();

        // 비콘 위치 정보를 2차원 배열로 변환한다
        double[][] positions = getBeaconPositions(beacons, beaconIds);

        // 삼변 측량 기법을 사용하여 사용자 위치를 계산 및 반환한다
        return findUserLocationUsingTrilateration(positions, distances);
    }

    // 비콘 객체 목록을 사용하여 각 비콘의 위치 정보를 2차원 배열로 추출 및 반환
    public double[][] getBeaconPositions(List<Beacon> beacons, List<Long> beaconIds) {

        double[][] positions = new double[beaconIds.size()][2];

        int index = 0;
        for (Long beaconId : beaconIds) {
            Beacon targetBeacon = beacons.stream()
                    .filter(beacon -> beacon.getBeaconId().equals(beaconId))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON)); // 비콘 ID에 해당하는 객체가 없으면 예외 throw
            positions[index][0] = targetBeacon.getLocation().getX();
            positions[index][1] = targetBeacon.getLocation().getY();
            index++;
        }

        return positions;
    }

//    // 비콘 ID 목록과 각 비콘의 전송전력 값을 사용하여 사용자와 각 비콘 사이의 거리를 계산
//    public double[] getBeaconDistances(List<Long> beaconIds, int[] txPowers) {
//
//        double[] distances = new double[beaconIds.size()];
//
//        for (int i = 0; i < beaconIds.size(); i++) {
//            Long beaconId = beaconIds.get(i);
//            // 각 비콘 ID에 해당하는 userBeaconOpt 객체를 찾는다
//            Optional<UserBeacon> userBeaconOpt = beaconRepository.findByBeaconId(beaconId);
//
//            // userBeaconOpt 객체가 존재하면, 해당 객체가 받은 RSSI 값을 사용하여 거리를 계산 및 distance 배열에 저장한다
//            if (userBeaconOpt.isPresent()) {
//                UserBeacon userBeacon = userBeaconOpt.get();
//                Short rssi = userBeacon.getRssi();
//                // 공식 : distance = 10 ^ ( (txPower - RSSI) / 10 )
//                distances[i] = Math.pow(10, (double) (txPowers[i] - rssi) / 10.0);
//            }
//        }
//
//        return distances;
//    }

    // 비콘 위치 정보와 사용자와 각 비콘 사이의 거리 정보를 통해 삼변 측량 기법을 사용하여 사용자 위치를 계산
    public Location findUserLocationUsingTrilateration(double[][] positions, double[] distances) {

        // TrilaterationFunction 객체를 생성 및 비콘 위치와 거리 정보를 전달한다
        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);

        // LeastSquaresOptimizer 클래스의 인스턴스 생성
        LeastSquaresOptimizer leastSquaresOptimizer = new LevenbergMarquardtOptimizer();

        // LeastSquaresOptimizer.Optimum 호출하여 최적화 작업을 수행
        // 이 과정에서 최소제곱법을 사용하여 삼변 측량 기법으로 사용자 위치를 계산한다
        LeastSquaresOptimizer.Optimum optimum = leastSquaresOptimizer
                .optimize(new LeastSquaresBuilder()
                        .start(new double[]{0, 0})
                        .model(trilaterationFunction)
                        .target(distances)
                        .weight(new DiagonalMatrix(distances))
                        .build());

        // 계산된 사용자 위치를 double 배열로 추출한다
        double[] calculatedLocation = optimum.getPoint().toArray();

        return new Location(calculatedLocation[0],calculatedLocation[1]);
    }

    // 모든 비콘 목록을 가져옴
    private List<Beacon> getAllBeacons() {

        return beaconRepository.findAll();
    }
}
