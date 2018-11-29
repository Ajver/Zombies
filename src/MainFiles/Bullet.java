package MainFiles;

import jslEngine.jslObject;

import java.awt.*;

public class Bullet extends jslObject {

    public Bullet(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void update(float et) {

    }

    public void render(Graphics g) {
        g.setColor(new Color(0,0,0));
        g.fillOval((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}
