package kr.co.monitoringserver.infra.filter;

public class KalmanFilter {
    private double Q = 0.00001;
    private double R = 0.001;
    private double X = 0, P = 1, K;

    // 생성자에는 초기값을 넣어주어야 한다.
    public KalmanFilter(double initValue) {
        X = initValue;
    }

    // 현재값을 받아 계산된 공식을 적용하고 반환한다
    public double update(double measurement) {
        measurementUpdate();

        X = X + (measurement - X) * K;

        return X;
    }

    // 이전의 값들을 공식을 이용하여 계산한다.
    private void measurementUpdate() {
        K = (P + Q) / (P + Q + R);
        P = R * (P + Q) / (R + P + Q);
    }

}
