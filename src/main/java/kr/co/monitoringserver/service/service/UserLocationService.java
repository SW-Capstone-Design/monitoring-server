package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.TrilaterationFunction;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserBeaconRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLocationService {

    private final BeaconRepository beaconRepository;

    private final UserBeaconRepository userBeaconRepository;


    private double[] calculateDistanceByBeaconIds(List<Long> beaconIds) {

        double[] distances = new double[beaconIds.size()];

        for (int i = 0; i < beaconIds.size(); i++) {
            Long beaconId = beaconIds.get(i);
            Beacon beacon = beaconRepository.findById(beaconId)
                    .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));
            int txPower = beacon.getTxPower();

            UserBeacon userBeacon = userBeaconRepository.findByUserBeaconId(beaconId)
                    .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));
            distances[i] = getBeaconDistances(txPower, userBeacon.getRssi());
        }

        return distances;
    }

    private double getBeaconDistances(int txPower, Short rssi) {

        return Math.pow(10, (double) (txPower - rssi) / 10.0);
    }

    // 비콘 객체 목록을 사용하여 각 비콘의 위치 정보를 2차원 배열로 추출 및 반환
    private double[][] getBeaconPositions(List<Beacon> beacons, List<Long> beaconIds) {

        double[][] positions = new double[beaconIds.size()][2];

        for (int i = 0; i < beaconIds.size(); i++) {
            Long beaconId = beaconIds.get(i);
            Beacon targetBeacon = findBeaconById(beacons, beaconId);
            positions[i] = targetBeacon.getLocation().positionArray();
        }

        return positions;
    }

    private Beacon findBeaconById(List<Beacon> beacons, Long beaconId) {

        return beacons.stream()
                .filter(beacon -> beacon.getBeaconId().equals(beaconId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));
    }

    // 각 비콘의 위치와 거리를 도출한 후 삼변측량을 사용해 사용자 위치를 계산
    public Location determineUserLocationWithTrilateration(List<Long> beaconIds) {

        // 데이터베이스에서 모든 비콘 객체를 가져온다
        List<Beacon> beacons = getAllBeacons();

        double[] distances = calculateDistanceByBeaconIds(beaconIds);

        // 비콘 위치 정보를 2차원 배열로 변환한다
        double[][] positions = getBeaconPositions(beacons, beaconIds);

        // 삼변 측량 기법을 사용하여 사용자 위치를 계산 및 반환한다
        return findUserLocationUsingTrilateration(positions, distances);
    }

    // 비콘 위치 정보와 사용자와 각 비콘 사이의 거리 정보를 통해 삼변 측량 기법을 사용하여 사용자 위치를 계산
    private Location findUserLocationUsingTrilateration(double[][] positions, double[] distances) {

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
