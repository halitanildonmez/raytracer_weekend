package com.company.renderer.material;

import com.company.renderer.core.Ray;
import com.company.renderer.math.Vector3;
import com.company.renderer.objects.HitRecord;
import com.company.renderer.util.RendererUtils;

public class Lambertian implements Material{

    private final Vector3 albedo;

    public Lambertian(Vector3 albedo) {
        this.albedo = albedo;
    }
    @Override
    public MaterialScatterResult scatter(Ray ray, HitRecord record, Vector3 attenuation, Ray scattered) {
        Vector3 scatterDirection = record.getNormal().add(RendererUtils.randomInUnitSphere());
        if (scatterDirection.nearZero()) {
            scatterDirection = record.getNormal();
        }

        scattered = new Ray(record.getP(), scatterDirection);
        attenuation = albedo;
        return new MaterialScatterResult(attenuation, scattered);
    }
}
