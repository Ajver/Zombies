package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends jslObject {

    public Wall(float x, float y, float w, float h) {
        super(x, y, w, h);
        this.setLabel(jslLabel.WALL);
    }

    public void render(Graphics g) {
        g.setColor(new Color(0,255, 0));
        g.drawRect((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}
