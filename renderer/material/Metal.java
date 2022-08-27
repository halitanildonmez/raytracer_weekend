package com.company.renderer.material;

import com.company.renderer.core.Ray;
import com.company.renderer.math.Vector3;
import com.company.renderer.objects.HitRecord;
import com.company.renderer.util.RendererUtils;

public class Metal implements Material {

    private final Vector3 albedo;
    private final double fuzz;

    public Metal(Vector3 albedo, double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz < 1.0 ? fuzz : 1;
    }

    @Override
    public MaterialScatterResult scatter(Ray ray, HitRecord record, Vector3 attenuation, Ray scattered) {
        Vector3 reflected = RendererUtils.reflect(RendererUtils.unitVector(ray.getDirection()), record.getNormal());
        scattered = new Ray(record.getP(), reflected.add(RendererUtils.randomInUnitSphere().mul(fuzz)));
        attenuation = albedo;
        if (RendererUtils.dot(scattered.getDirection(), record.getNormal()) > 0) {
            return new MaterialScatterResult(attenuation, scattered);
        }
        return null;
    }
}
