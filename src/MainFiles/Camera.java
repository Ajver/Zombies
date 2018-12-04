package MainFiles;

import jslEngine.jslObject;
import jslEngine.jslVector2;

import java.util.Random;

public class Camera {

    private static float x, y;
    private static float velX, velY;

    private static boolean isShaking = false;
    private static jslVector2 vShake;
    private static float power = 0.0f;

    private static jslObject o;


    public Camera(jslObject o) {
        focus(o);
    }

    public static void update(float et) {
        setVelocity();

        if(isShaking) {
            if(power > 0.1f) {
                shake(power * (0.8f - et * 4.0f));
            }else {
                vShake.set(0, 0);
                isShaking = false;
            }
        }

        x += (velX + vShake.x) * et;
        y += (velY + vShake.y) * et;
    }

    public static void focus(jslObject o) {
        if(o != null) {
            Camera.o = o;

            x = o.getX() - MainClass.WW / 2.0f + o.getW() / 2.0f;
            y = o.getY() - MainClass.WH / 2.0f + o.getH() / 2.0f;

            vShake = new jslVector2(0, 0);
        }
    }

    private static void setVelocity() {
        if(o != null) {
            float speed = 7.0f;
            velX = ((o.getX() - MainClass.WW / 2.0f + o.getW() / 2.0f) - x) * speed;
            velY = ((o.getY() - MainClass.WH / 2.0f + o.getH() / 2.0f) - y) * speed;
        }
    }

    public static void shake(float power) {
        isShaking = true;

        Random r = new Random();
        float x = (r.nextInt(100) - 50) / 50.0f;
        float y = (float)Math.sqrt(1.0f - x*x) - 0.5f;

        vShake.set(x, y);
        vShake.multiply(power);

        Camera.power = power;
    }

    public static float getX() { return x; }
    public static float getY() { return y; }
}
