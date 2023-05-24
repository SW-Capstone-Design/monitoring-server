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

            UserBeacon userBeacon = userBeaconRepository.findByBeacon_BeaconId(beaconId)
                    .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));

            distances[i] = getBeaconDistances(txPower, userBeacon.getRssi());
        }

        return distances;
    }

    // 비콘과 사용자 사이의 거리를 계산
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

        List<Beacon> beacons = getAllBeacons();

        double[] distances = calculateDistanceByBeaconIds(beaconIds);

        double[][] positions = getBeaconPositions(beacons, beaconIds);

        return findUserLocationUsingTrilateration(positions, distances);
    }

    // 비콘 위치 정보와 사용자와 각 비콘 사이의 거리 정보를 통해 삼변 측량 기법을 사용하여 사용자 위치를 계산
    private Location findUserLocationUsingTrilateration(double[][] positions, double[] distances) {

        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);

        int maxEval = 1000; // 최대 평가 횟수 설정

        int maxIter = 1000; // 최대 반복 횟수 설정

        LeastSquaresOptimizer leastSquaresOptimizer = new LevenbergMarquardtOptimizer();

        // 이 과정에서 최소제곱법을 사용하여 삼변 측량 기법으로 사용자 위치를 계산한다
        LeastSquaresOptimizer.Optimum optimum = leastSquaresOptimizer
                .optimize(new LeastSquaresBuilder()
                        .maxEvaluations(maxEval)    // 설정된 최대 평가 횟수를 추가함
                        .maxIterations(maxIter)     // 설정된 최대 반복 횟수를 추가함
                        .start(new double[]{0, 0})
                        .model(trilaterationFunction)
                        .target(distances)
                        .weight(new DiagonalMatrix(distances))
                        .build());

        double[] calculatedLocation = optimum.getPoint().toArray();

        return new Location(calculatedLocation[0],calculatedLocation[1]);
    }

    // 모든 비콘 목록을 가져옴
    private List<Beacon> getAllBeacons() {

        return beaconRepository.findAll();
    }
}
