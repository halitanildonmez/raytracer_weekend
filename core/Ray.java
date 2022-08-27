package com.company.renderer.core;

import com.company.renderer.math.Vector3;

public class Ray {

    private final Vector3 origin;
    private final Vector3 direction;
    private final double time;

    public Ray() {
        origin = Vector3.ZERO;
        direction = Vector3.ZERO;
        time = 0.0;
    }

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
        time = 0.0;
    }

    public Ray(Vector3 origin, Vector3 direction, double time) {
        this.origin = origin;
        this.direction = direction;
        this.time = time;
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public double getTime() {
        return time;
    }

    public Vector3 at(double t) {
        Vector3 mul = this.direction.mul(t);
        return origin.add(mul);
    }
}
