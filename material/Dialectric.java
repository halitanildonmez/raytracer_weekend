package com.company.renderer.material;

import com.company.renderer.core.Ray;
import com.company.renderer.math.Vector3;
import com.company.renderer.objects.HitRecord;
import com.company.renderer.util.RendererUtils;

public class Dialectric implements Material{

    private final double ir;

    public Dialectric(double ir) {
        this.ir = ir;
    }

    @Override
    public MaterialScatterResult scatter(Ray ray, HitRecord record, Vector3 attenuation, Ray scattered) {
        attenuation = Vector3.ONE;
        double reflectionRatio = record.isFrontFace() ? (1.0 / ir) : ir;
        Vector3 unitDirection = RendererUtils.unitVector(ray.getDirection());
        double cosTheta = Math.min(RendererUtils.dot(unitDirection.mul(-1), record.getNormal()), 1.0);
        double sinTheta = Math.sqrt(1.0 - cosTheta*cosTheta);
        boolean cannotRefact = reflectionRatio * sinTheta > 1.0;
        Vector3 direction;
        if (cannotRefact || reflectance(cosTheta, reflectionRatio) > RendererUtils.randomDouble()) {
            direction = RendererUtils.reflect(unitDirection, record.getNormal());
        } else {
            direction = RendererUtils.refract(unitDirection, record.getNormal(), reflectionRatio);
        }
        scattered = new Ray(record.getP(), direction);
        return new MaterialScatterResult(attenuation, scattered);
    }

    private static double reflectance(double cos, double reflectanceIdx) {
        double r0 = (1 - reflectanceIdx) / (1 + reflectanceIdx);
        r0 = r0 * r0;
        return r0 + (1 - r0) * Math.pow((1 - cos), 5);
    }
}
