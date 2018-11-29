package MainFiles;

import jslEngine.jslObject;

public class Camera {

    private float x, y;
    private float velX, velY;

    private jslObject o;


    public Camera(jslObject o) {
        this.o = o;

        this.x = o.getX() - MainClass.WW / 2.0f + o.getW() / 2.0f;
        this.y = o.getY() - MainClass.WH / 2.0f + o.getH() / 2.0f;
    }

    public void update(float et) {
        setVelocity();

        x += velX * et;
        y += velY * et;
    }

    public void focus(jslObject o) {
        if(o != null) {
            this.o = o;
        }
    }

    private void setVelocity() {
        if(o != null) {
            float speed = 7.0f;
            this.velX = ((o.getX() - MainClass.WW / 2.0f + o.getW() / 2.0f) - this.x) * speed;
            this.velY = ((o.getY() - MainClass.WH / 2.0f + o.getH() / 2.0f) - this.y) * speed;
        }
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
