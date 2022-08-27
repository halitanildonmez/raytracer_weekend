package com.company.renderer.core;

import com.company.renderer.math.Vector3;
import com.company.renderer.util.RendererUtils;

public class Camera {
    private final Vector3 origin;
    private final Vector3 lowerLeftCorner;
    private final Vector3 horizontal;
    private final Vector3 vertical;
    private final Vector3 u;
    private final Vector3 v;
    private final Vector3 w;
    private final double lensRadius;
    private final double time0;
    private final double time1;

    public Camera(Vector3 lookFrom, Vector3 lookAt, Vector3 up, double vfov, double aspectRatio, double aperture,
                  double focusDist, double t0, double t1) {
        double theta = RendererUtils.degreesToRadians(vfov);
        double h = Math.tan(theta / 2.0);
        double viewportHeight = 2.0 * h;
        double viewportWidth = aspectRatio * viewportHeight;

        this.w = RendererUtils.unitVector(lookFrom.subtract(lookAt));
        this.u = RendererUtils.unitVector(RendererUtils.cross(up, this.w));
        this.v = RendererUtils.cross(this.w, this.u);

        this.origin = lookFrom;
        this.horizontal = this.u.mul(focusDist * viewportWidth);
        this.vertical = this.v.mul(focusDist * viewportHeight);
        this.lowerLeftCorner = origin.subtract(horizontal.div(2.0)).subtract(vertical.div(2.0)).subtract(this.w.mul(focusDist));

        this.lensRadius = aperture / 2.0;
        this.time0 = t0;
        this.time1 = t1;
    }

    public Ray getRay(double s, double t) {
        Vector3 rd = RendererUtils.randomInUnitDisk().mul(lensRadius);
        Vector3 offset = u.mul(rd.getX());
        offset = offset.add(v.add(rd.getY()));
        Vector3 a = lowerLeftCorner.add(horizontal.mul(s));
        a = a.add(vertical.mul(t));
        a = a.subtract(origin);
        a = a.subtract(offset);
        return new Ray(origin.add(offset), a, RendererUtils.randomDouble(time0, time1));
    }
}
