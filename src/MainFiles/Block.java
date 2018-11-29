package MainFiles;

import jslEngine.jslObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends jslObject {

    private BufferedImage texture = Texture.blockImg;

    public Block(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    public void render(Graphics g) {
//        g.setColor(new Color(0,0, 255));
//        g.fillRect((int)getX(), (int)getY(), (int)getW(), (int)getH());

        g.drawImage(texture, (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}