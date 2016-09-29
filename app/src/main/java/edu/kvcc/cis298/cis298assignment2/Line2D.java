package edu.kvcc.cis298.cis298assignment2;

/**
 * Created by doc on 9/27/16.
 */

public class Line2D {
    public double mSlope;
    public double mYIntercept;

    // Arguments P and Q are coordinate pairs on a two
    // dimensional cartesian plane
    public Line2D(double P[], double Q[]) {
        // Store equation for line in y-intercept (y=mx+b) form
        // Truncate doubles to two decimal points
        mSlope = Math.floor((Q[1] - P[1]) / (Q[0] - P[0]) * 100) / 100;
        mYIntercept = Math.floor(P[1] - (mSlope * P[0]) * 100) / 100;
    }

    // Get the return value of the function of the line
    // decimalPlaces = -1 to ignore truncation
    public double solution(double x, int decimalPlaces) {
        double solution = (mSlope * x) + mYIntercept;

        // Truncate solution
        if (decimalPlaces > -1) {
            double factor = Math.pow(10, decimalPlaces);
            solution = Math.floor(solution * factor) / factor;
        }
        return solution;
    }

    // Get the inverse return value of the function of the line
    public double i(double y) { return (y - mYIntercept) / mSlope; }

    // Return the equation of the line in string form
    @Override
    public String toString() {
        String yInt = (mYIntercept >= 0) ? ("+ " + mYIntercept) : ("- " + (-mYIntercept));
        String str = "solution(x) = " + mSlope + "(x) " + yInt;
        return str;
    }
}
