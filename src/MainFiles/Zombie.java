package MainFiles;

import jslEngine.jslObject;
import jslEngine.jslVector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends jslObject {

    private BufferedImage texture = Texture.zombieImg;
    private Player player;

    private float vel;

    public Zombie(float x, float y, float w, float h, float vel, Player player) {
        super(x, y, w, h);
        this.vel = vel;
        this.player = player;
        this.setRotateToCenter();
    }

    public void update(float et) {
        jslVector2 v = new jslVector2(player.getX() - getX(), player.getY() - getY());
        v.normalize();
        v.multiply(vel);
        setVel(v);

        float theta = (float)Math.atan2(v.x, v.y);
        setRotate(2*(float)Math.PI - theta);
    }

    public void render(Graphics g) {
        g.drawImage(texture, (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}
