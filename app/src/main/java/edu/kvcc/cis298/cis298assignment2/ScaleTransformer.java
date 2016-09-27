package edu.kvcc.cis298.cis298assignment2;

/**
 * Created by doc on 9/27/16.
 */

public class ScaleTransformer {
    private final Line2D Formula;

    public ScaleTransformer(TempScale tempFrom, TempScale tempTo) {
        double p[] = {tempFrom.FreezingTemp, tempTo.FreezingTemp};
        double q[] = {tempFrom.BoilingTemp, tempTo.BoilingTemp};
        Formula = new Line2D(p, q);
    }

    public double convert(double deg) {
        return Formula.f(deg);
    }

    @Override
    public String toString() {
        return Formula.toString();
    }
}
