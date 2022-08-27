package com.company.renderer.util;

import com.company.renderer.math.Vector3;

public class RendererUtils {

    static double PI = 3.1415926535897932385;

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static double randomDouble(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }

    public static double randomDouble() {
        return randomDouble(0, 1);
    }

    public static double dot(Vector3 u, Vector3 v) {
        return (u.getX() * v.getX()) + (u.getY() * v.getY()) +
                (u.getZ() * v.getZ());
    }

    public static Vector3 cross(Vector3 u, Vector3 v) {
        return new Vector3(u.getY() * v.getZ() - u.getZ() * v.getY(),
                u.getZ() * v.getX() - u.getX() * v.getZ(),
                u.getX() * v.getY() - u.getY() * v.getX());
    }

    public static Vector3 unitVector(Vector3 v) {
        return v.div(v.length());
    }

    public static Vector3 random() {
        return new Vector3(randomDouble(), randomDouble(), randomDouble());
    }

    public static Vector3 random(double min, double max) {
        return new Vector3(randomDouble(min, max), randomDouble(min, max), randomDouble(min, max));
    }

    public static Vector3 randomVectorDouble(double min, double max) {
        return new Vector3(randomDouble(min, max), randomDouble(min, max), randomDouble(min, max));
    }

    public static Vector3 randomInUnitSphere() {
        while (true) {
            Vector3 v = randomVectorDouble(-1.0, 1.0);
            if (v.lengthSquared() >= 1) {
                continue;
            }
            return v;
        }
    }

    public static Vector3 reflect(Vector3 v, Vector3 n) {
        double v1 = 2 * dot(v, n);
        return v.subtract(n.mul(v1));
    }

    public static Vector3 refract(Vector3 uv, Vector3 n, double etaiOverEtat) {
        double cosTheta = Math.min(dot(uv.mul(-1), n), 1.0);
        Vector3 rOutPerp = n.mul(cosTheta);
        rOutPerp = rOutPerp.add(uv);
        rOutPerp = rOutPerp.mul(etaiOverEtat);
        double tmp = -1 * Math.sqrt(Math.abs(1.0 - rOutPerp.lengthSquared()));
        Vector3 rOutParallel = n.mul(tmp);
        return rOutPerp.add(rOutParallel);
    }

    public static double degreesToRadians(double degrees) {
        return degrees * PI / 180.0;
    }

    public static Vector3 randomInUnitDisk() {
        while (true) {
            Vector3 p = new Vector3(randomDouble(-1,1), randomDouble(-1,1), 0);
            if (p.lengthSquared() >= 1) continue;
            return p;
        }
    }
}
