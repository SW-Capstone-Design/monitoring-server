package kr.co.monitoringserver.persistence.entity.beacon;

import org.apache.commons.math3.fitting.leastsquares.MultivariateJacobianFunction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;

public class TrilaterationFunction implements MultivariateJacobianFunction {

    private final double[][] positions;

    private final double[] distances;


    public TrilaterationFunction(double[][] positions,
                                 double[] distances) {

        this.positions = positions;
        this.distances = distances;
    }

    @Override
    public Pair<RealVector, RealMatrix> value(RealVector point) {

        // 오버라이딩 한 메서드에서 필요한 RealVector 와 RealMatrix 계산
        RealVector residual = calculateResidual(point);
        RealMatrix matrix = calculateMatrix(point);

        // Pair<RealVector, RealMatrix> 형태로 반환
        return new Pair<>(residual, matrix);
    }


    private RealMatrix calculateMatrix(RealVector point) {

        double[][] result = new double[distances.length][point.getDimension()];

        for (int i = 0; i < result.length; ++i) {
            for (int j = 0; j < point.getDimension(); ++j) {
                result[i][j] = 2 * (point.getEntry(j) - positions[i][j]);
            }
        }

        return new Array2DRowRealMatrix(result, false);
    }

    private RealVector calculateResidual(RealVector point) {

        double[] result = new double[positions.length];

        for (int i = 0; i < positions.length; ++i) {
            double sum = 0;
            for (int j = 0; j < point.getDimension(); ++j) {
                sum += Math.pow(point.getEntry(j) - positions[i][j], 2);
            }
            result[i] = sum - Math.pow(distances[i], 2);
        }
        return new ArrayRealVector(result);
    }
}
