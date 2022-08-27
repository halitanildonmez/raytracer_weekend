package com.company.renderer;

import com.company.renderer.core.Camera;
import com.company.renderer.core.Ray;
import com.company.renderer.material.*;
import com.company.renderer.math.Vector3;
import com.company.renderer.objects.HitRecord;
import com.company.renderer.objects.Hittable;
import com.company.renderer.objects.HittableList;
import com.company.renderer.objects.Sphere;

import java.io.BufferedWriter;
import java.io.FileWriter;

import static com.company.renderer.util.RendererUtils.*;

public class RendererMain {

    private static final int WIDTH = 800;
    private static final double DIST_TO_FOCUS = 10.0;
    private static final double APERTURE = 0.1;
    private static final int SAMPLES = 100;
    private static final int ASPECT_RATIO = 1;
    private static final double T_MIN = 0.001;
    private static final double VFOV = 20.0;
    private static final int NUM_RANDOM_SPHERES = 10;
    private static final int DEPTH = 10;

    private static final Vector3 LOOK_FROM = new Vector3(13, 2, 3);
    private static final Vector3 LOOK_AT = new Vector3(0, 0, 0);
    private static final Vector3 VUP = new Vector3(0, 1, 0);

    public static Vector3 getRayColor(Ray ray, Hittable hittable, int depth) {
        if (depth <= 0)
            return Vector3.ZERO;

        HitRecord hitRecord = hittable.hit(ray, T_MIN, Double.MAX_VALUE);

        if (hitRecord != null) {
            Material material = hitRecord.getMaterial();
            MaterialScatterResult scatter = material.scatter(ray, hitRecord, new Vector3(), new Ray());
            if (scatter != null) {
                Vector3 rayColor = getRayColor(scatter.getScattered(), hittable, depth - 1);
                return scatter.getAttenuation().mul(rayColor);
            }
        }

        Vector3 unitDirection = unitVector(ray.getDirection());
        double t = 0.5 * (unitDirection.getY() + 1.0);

        Vector3 first = Vector3.ONE.mul(1.0 - t);
        Vector3 second = new Vector3(0.5, 0.7, 1.0).mul(t);
        return first.add(second);
    }

    private static HittableList createWorld() {
        HittableList world = new HittableList();
        Material lambertian = new Lambertian(new Vector3(0.5, 0.5, 0.5));
        world.addObject(new Sphere(new Vector3(0, -1000, 0), 1000, lambertian));

        for (int i = -NUM_RANDOM_SPHERES; i < NUM_RANDOM_SPHERES; i++) {
            for (int j = -NUM_RANDOM_SPHERES; j < NUM_RANDOM_SPHERES; j++) {
                double chooseMat = randomDouble();
                Vector3 center = new Vector3(i + 0.9 * randomDouble(), 0.2, j + 0.9 * randomDouble());

                if ((center.subtract(new Vector3(4.0, 0.2, 0.0)).length() > 0.9)) {
                    if (chooseMat < 0.8) {
                        Vector3 albedo = random().mul(random());
                        Material lamb = new Lambertian(albedo);
                        world.addObject(new Sphere(center, 0.2, lamb));
                    } else if (chooseMat < 0.95) {
                        Vector3 albedo = random(0.5, 1.0);
                        double fuzz = randomDouble(0.0, 0.5);
                        Material mat = new Metal(albedo, fuzz);
                        world.addObject(new Sphere(center, 0.2, mat));
                    } else {
                        Material mat = new Dialectric(1.5);
                        world.addObject(new Sphere(center, 0.2, mat));
                    }
                }
            }
        }

        Material mat1 = new Dialectric(1.5);
        world.addObject(new Sphere(new Vector3(0, 1, 0), 1.0, mat1));
        Material mat2 = new Lambertian(new Vector3(0.4, 0.2, 0.1));
        world.addObject(new Sphere(new Vector3(-4, 1, 0), 1.0, mat2));
        Material mat3 = new Metal(new Vector3(0.7, 0.6, 0.5), 0.0);
        world.addObject(new Sphere(new Vector3(4, 1, 0), 1.0, mat3));

        return world;
    }

    public static void main(String[] args) {

        PPMColor ppmColor = new PPMColor();

        int imageWidth = WIDTH;
        int imageHeight = WIDTH / ASPECT_RATIO;

        HittableList world = createWorld();

        Camera cam = new Camera(LOOK_FROM, LOOK_AT, VUP, VFOV, ASPECT_RATIO, APERTURE, DIST_TO_FOCUS, 0.0, 0.0);

        StringBuilder ppmFile = new StringBuilder("P3\n" + imageWidth + " " + imageHeight + "\n255\n");

        for (int j = imageHeight - 1; j >= 0; j--) {
            System.out.println("\rScanlines remaining " + j);
            for (int i = 0; i < imageWidth; i++) {
                Vector3 color = Vector3.ZERO;
                for (int s = 0; s < SAMPLES; s++) {
                    double u = (i + randomDouble()) / ((double)(imageWidth - 1));
                    double v = (j + randomDouble()) / ((double)(imageHeight - 1));
                    Ray r = cam.getRay(u, v);
                    color = color.add(getRayColor(r, world, DEPTH));
                }
                ppmFile.append(ppmColor.writeColor(color, SAMPLES));
            }
        }

        writeToFile(ppmFile.toString());
    }

    private static void writeToFile (String ppm) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("out.ppm"))){
            writer.write(ppm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
