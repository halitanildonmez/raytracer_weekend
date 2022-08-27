package com.company.renderer.objects;

import com.company.renderer.core.Ray;
import com.company.renderer.material.Material;
import com.company.renderer.math.Vector3;
import com.company.renderer.util.RendererUtils;

public class HitRecord {
    private Vector3 p;
    private Vector3 normal;
    private Material material;
    private double t;
    private boolean frontFace;

    public HitRecord() {
        p = Vector3.ZERO;
        normal = Vector3.ZERO;
        material = null;
        t = Double.MIN_VALUE;
        frontFace = false;
    }

    public Vector3 getP() {
        return p;
    }

    public void setP(Vector3 p) {
        this.p = p;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public void setNormal(Vector3 normal) {
        this.normal = normal;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public boolean isFrontFace() {
        return frontFace;
    }

    public void setFrontFace(boolean frontFace) {
        this.frontFace = frontFace;
    }

    public void setFaceNormal(Ray r, Vector3 outwardNormal) {
        setFrontFace(RendererUtils.dot(r.getDirection(), outwardNormal) < 0);
        setNormal(frontFace ? outwardNormal : outwardNormal.mul(-1));
    }
}
