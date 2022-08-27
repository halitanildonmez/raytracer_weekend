package com.company.renderer.material;

import com.company.renderer.core.Ray;
import com.company.renderer.math.Vector3;
import com.company.renderer.objects.HitRecord;

public interface Material {
    MaterialScatterResult scatter(Ray ray, HitRecord record, Vector3 attenuation, Ray scattered);
}
