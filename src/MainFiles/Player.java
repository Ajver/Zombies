package MainFiles;

import jslEngine.jslObject;

import java.awt.*;

public class Player extends jslObject {

    private float theta;

    public Player(float x, float y, float w, float h) {
        setPosition(x, y);
        setSize(w, h);
    }

    public void update(float et) {
        x += velX * et;
        y += velY * et;
    }

    public void render(Graphics g) {
        g.setColor(new Color(135, 111, 255));
        g.fillOval((int)getX(), (int)getY(), (int)getW(), (int)getH());

        g.setColor(new Color(46, 148, 34));
        g.fillRect((int)(getX() + getW() * 0.5f - 4), (int)(getY() - getH() * 0.5f), 8, 32);
    }

    public void setTheta(float t) {
        this.theta = t;
        this.setRotate(t);
    }
}
