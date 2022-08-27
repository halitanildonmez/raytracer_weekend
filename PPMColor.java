package com.company.renderer;

import com.company.renderer.math.Vector3;

import static com.company.renderer.util.RendererUtils.clamp;

public class PPMColor {
    public String writeColor(Vector3 color, int samples) {
        double r = color.getX();
        double g = color.getY();
        double b = color.getZ();

        double scale = 1.0 / samples;
        r = Math.sqrt(scale * r);
        g = Math.sqrt(scale * g);
        b = Math.sqrt(scale * b);

        int ri = (int)(256 * clamp(r, 0.0, 0.999));
        int gi = (int)(256 * clamp(g, 0.0, 0.999));
        int bi = (int)(256 * clamp(b, 0.0, 0.999));
        return ri + " " + gi + " " + bi + "\n";
    }
}
