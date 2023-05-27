package kr.co.monitoringserver.service.service;

import kr.co.monitoringserver.infra.global.exception.NotFoundException;
import kr.co.monitoringserver.infra.global.model.ResponseStatus;
import kr.co.monitoringserver.persistence.entity.Location;
import kr.co.monitoringserver.persistence.entity.beacon.Beacon;
import kr.co.monitoringserver.persistence.entity.beacon.TrilaterationFunction;
import kr.co.monitoringserver.persistence.entity.beacon.UserBeacon;
import kr.co.monitoringserver.persistence.entity.user.User;
import kr.co.monitoringserver.persistence.repository.BeaconRepository;
import kr.co.monitoringserver.persistence.repository.UserBeaconRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLocationService {

    private final UserBeaconRepository userBeaconRepository;

    private final BeaconRepository beaconRepository;


    // 각 Distances, Positions 값으로 삼변측량을 사용해 사용자 위치를 반환
    @Transactional
    public Location updateUserLocationByTrilateration(User user) {

        List<Beacon> beaconList = beaconRepository.findAll();

        // 비콘의 수가 3개 미만인 경우 확인
        if (beaconList.size() < 3) {
            // 여기에서 적절한 예외를 발생시키거나 처리를 수행합니다.
            throw new RuntimeException("삼변측량을 위한 최소 비콘의 수를 충족하지 못했습니다");
        }

        double[] distances = getDistancesFromBeaconAndUserBeacon(beaconList, user);

        double[][] positions = convertBeaconPositions(beaconList);

        return calculateUserLocationByTrilateration(positions, distances);
    }

    // 비콘의 txPower 값과 사용자-비콘의 RSSI 값을 각 테이블에서 추출
    private double[] getDistancesFromBeaconAndUserBeacon(List<Beacon> beaconList, User user) {

        // 비콘의 TxPower 값 가져오기: 데이터베이스나 기타 레포지토리에서 모든 비콘의 TxPower 값을 가져옵니다.
        int[] txPowers = new int[beaconList.size()];

        for (int i = 0; i < beaconList.size(); i++) {
            txPowers[i] = beaconList.get(i).getTxPower();
        }

        // 사용자-비콘 rssi 값 가져오기: 데이터베이스나 기타 레포지토리에서 각 사용자에 대한 모든 비콘과의 rssi 값을 가져옵니다.
        short[] rssiValues = new short[beaconList.size()];

        for (int i = 0; i < beaconList.size(); i++) {
            rssiValues[i] = getUserBeaconRSSI(beaconList.get(i).getBeaconId(), user);
        }

        // 거리 계산: 가져온 TxPower 값과 rssi 값을 사용해서, 각 사용자와 비콘 간의 거리를 계산합니다.
        double[] distances = new double[beaconList.size()];

        for (int i = 0; i < beaconList.size(); i++) {
            distances[i] = calculateBeaconAndUserDistances(txPowers[i], rssiValues[i]);
        }

        return distances;
    }

    // 사용자-비콘의 RSSI 값을 추출
    private short getUserBeaconRSSI(Long beaconId, User user) {

        final Beacon beacon = beaconRepository.findById(beaconId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));

        final UserBeacon userBeacon = userBeaconRepository.findByUserAndBeacon(user, beacon)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.NOT_FOUND_BEACON));

        return userBeacon.getRssi();
    }

    // 비콘의 txPower 값과 사용자-비콘의 RSSI 값을 통해 거리를 계산하는 공식
    private double calculateBeaconAndUserDistances(int txPower, Short rssi) {

        double ratio = rssi * 1.0 / txPower;
        double distance;

        // RSSI 와 txPower 값에 따라 거리를 계산
        if (ratio < 1.0) {
            distance = Math.pow(ratio, 10);
        } else {
            distance = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
        }

        return distance;
    }

    // 비콘의 위치 정보를 2차원 배열로 변환
    private double[][] convertBeaconPositions(List<Beacon> beaconList) {

        double[][] positions = new double[beaconList.size()][2];

        for (int i = 0; i < beaconList.size(); i++) {

            Beacon beacon = beaconList.get(i);

            positions[i][0] = beacon.getLocation().getX();
            positions[i][1] = beacon.getLocation().getY();
        }

        return positions;
    }

    // 최소제곱법을 사용해 삼변 측량 기법으로 사용자 위치를 계산
    private Location calculateUserLocationByTrilateration(double[][] positions, double[] distances) {

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
}
