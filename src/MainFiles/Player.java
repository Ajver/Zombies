package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends jslObject {

    private BufferedImage texture = Texture.playerImg;

    public Player(float x, float y, float w, float h) {
        super(x, y, w, h);
        this.setLabel(jslLabel.PLAYER);
        this.setRotateToCenter();
    }

    public void update(float et) {}

    public void render(Graphics g) {
//
//        g.setColor(new Color(135, 111, 255));
//        g.fillOval((int)getX(), (int)getY(), (int)getW(), (int)getH());
//
//        g.setColor(new Color(46, 148, 34));
//        g.fillRect((int)(getX() + getW() * 0.5f - 4), (int)(getY() - getH() * 0.5f), 8, 32);

        g.drawImage(texture, (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}
