package com.company.renderer.objects;

import com.company.renderer.core.Ray;

import java.util.ArrayList;
import java.util.List;

public class HittableList implements Hittable{

    private final List<Hittable> objects;

    public HittableList() {
        objects = new ArrayList<>();
    }

    public void addObject(Hittable h) {
        objects.add(h);
    }

    public void add(List<Hittable> h) {
        objects.addAll(h);
    }

    @Override
    public HitRecord hit(Ray r, double tMin, double tMax) {
        HitRecord rec = null;
        double closest = tMax;
        for (Hittable obj : objects) {
            HitRecord hit = obj.hit(r, tMin, closest);
            if (hit != null) {
                rec = hit;
                closest = rec.getT();
            }
        }
        return rec;
    }
}
