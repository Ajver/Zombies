package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
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
        this.setLabel(jslLabel.ZOMBIE);
    }

    public void update(float et) {
        jslVector2 v = new jslVector2(player.getX() - getX(), player.getY() - getY());
        v.normalize();
        v.multiply(vel);
        setVel(v);

        float theta = (float)Math.atan2(v.x, v.y);
        setRotate(2*(float)Math.PI - theta);
    }

    public void onCollision(jslObject other) {
        if(other.getLabel() != jslLabel.BULLET) {
            collisionBox.bound(other);
            if (other.getLabel() == jslLabel.PLAYER) {
//            collisionBox.bound(other);
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(texture, (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}
