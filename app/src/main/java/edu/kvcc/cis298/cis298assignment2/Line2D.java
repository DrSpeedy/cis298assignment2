package edu.kvcc.cis298.cis298assignment2;

/**
 * Created by doc on 9/27/16.
 */

public class Line2D implements ILine {
    public double Slope;
    public double YIntercept;

    // Arguments P and Q are coordinate pairs on a two
    // dimensional cartesian plane
    public Line2D(double P[], double Q[]) {
        // Store equation for line in y-intercept (y=mx+b) form
        Slope = (Q[1] - P[1]) / (Q[0] - P[0]);
        YIntercept = P[1] - (Slope * P[0]);
    }

    public double f(double x) {
        return (Slope * x) + YIntercept;
    }

    @Override
    public String toString() {
        String yInt = (YIntercept >= 0) ? ("+ " + YIntercept) : ("- " + (-YIntercept));
        String str = "" + Slope + "(x) " + yInt;

        return str;
    }
}
