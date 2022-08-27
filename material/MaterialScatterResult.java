package com.company.renderer.material;

import com.company.renderer.core.Ray;
import com.company.renderer.math.Vector3;

public class MaterialScatterResult {
    private final Vector3 attenuation;
    private final Ray scattered;

    public Vector3 getAttenuation() {
        return attenuation;
    }

    public Ray getScattered() {
        return scattered;
    }

    public MaterialScatterResult(Vector3 attenuation, Ray scattered) {
        this.attenuation = attenuation;
        this.scattered = scattered;
    }
}
