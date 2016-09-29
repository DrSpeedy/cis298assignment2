package edu.kvcc.cis298.cis298assignment2;

/**
 * Created by doc on 9/27/16.
 */

public class ScaleTransformer {
    private final Line2D mFormula;

    public ScaleTransformer(TempScale tempFrom, TempScale tempTo) {
        double p[] = {tempFrom.mFreezingTemp, tempTo.mFreezingTemp};
        double q[] = {tempFrom.mBoilingTemp, tempTo.mBoilingTemp};
        mFormula = new Line2D(p, q);
    }

    public double convert(double deg) {
        return mFormula.solution(deg, 2);
    }

    @Override
    public String toString() {
        return mFormula.toString();
    }
}
