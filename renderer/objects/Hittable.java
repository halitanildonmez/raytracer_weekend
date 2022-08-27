package com.company.renderer.objects;

import com.company.renderer.core.Ray;

public interface Hittable {

    HitRecord hit(Ray r, double tMin, double tMax);
}
