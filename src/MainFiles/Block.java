package MainFiles;

import jslEngine.jslObject;

import java.awt.*;

public class Block extends jslObject {

    public Block(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void render(Graphics g) {
        g.setColor(new Color(0,0, 255));
        g.fillRect((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}
