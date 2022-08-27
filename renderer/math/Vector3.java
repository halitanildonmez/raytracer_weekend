package com.company.renderer.math;

public class Vector3 {

    public static Vector3 ZERO = new Vector3(0, 0, 0);
    public static Vector3 ONE = new Vector3(1, 1, 1);

    private final double x;
    private final double y;
    private final double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector3 add(Vector3 e) {
        return new Vector3(x + e.getX(), y + e.getY(),
                z + e.getZ());
    }

    public Vector3 add(double e) {
        return new Vector3(x + e, y + e, z + e);
    }

    public Vector3 subtract(Vector3 e) {
        return new Vector3(x - e.getX(), y - e.getY(),
                z - e.getZ());
    }

    public Vector3 mul(double a) {
        return new Vector3(x * a, y * a, z * a);
    }

    public Vector3 mul(Vector3 a) {
        return new Vector3(x * a.getX(), y * a.getY(), z * a.getZ());
    }

    public Vector3 div(double a) {
        return new Vector3(x / a, y / a, z / a);
    }


    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared () {
        return x*x + y*y + z*z;
    }

    public boolean nearZero() {
        double s = Double.MIN_VALUE;
        return (Math.abs(x) < s) && (Math.abs(y) < s) && (Math.abs(z) < s);
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}
