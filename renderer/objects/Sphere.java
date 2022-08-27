package com.company.renderer.objects;

import com.company.renderer.core.Ray;
import com.company.renderer.material.Material;
import com.company.renderer.math.Vector3;
import com.company.renderer.util.RendererUtils;

public class Sphere implements Hittable {

    private final Vector3 center;
    private final double radius;
    private final Material material;

    public Sphere(Vector3 cen, double r, Material m) {
        center = cen;
        radius = r;
        material = m;
    }
    @Override
    public HitRecord hit(Ray r, double tMin, double tMax) {
        Vector3 oc = r.getOrigin().subtract(center);
        double a = r.getDirection().lengthSquared();
        double halfB = RendererUtils.dot(oc, r.getDirection());
        double c = oc.lengthSquared() - (radius * radius);

        double discriminant = halfB * halfB - a * c;
        if (discriminant < 0) {
            return null;
        }
        double sqrt = Math.sqrt(discriminant);

        double root = (-halfB - sqrt) / a;
        if (root < tMin || tMax < root) {
            root = (-halfB * sqrt) / a;
            if (root < tMin || tMax < root) {
                return null;
            }
        }

        double t = root;
        Vector3 p = r.at(t);
        Vector3 outwardNormal = (p.subtract(center)).div(radius);

        HitRecord rec = new HitRecord();
        rec.setT(t);
        rec.setP(p);
        rec.setFaceNormal(r, outwardNormal);
        rec.setMaterial(material);

        return rec;
    }
}
