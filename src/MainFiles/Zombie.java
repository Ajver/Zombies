package MainFiles;

import jslEngine.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends jslObject {

    private BufferedImage texture = Texture.zombieImg;
    private Player player;

    private float vel;

    private float strange;
    private float hitRate = 1.0f;
    private float timer = 1.0f / hitRate;
    private boolean isReady = false;
    
    public Zombie(float x, float y, float w, float h, float vel, Player player) {
        super(x, y, w, h);
        this.vel = vel;
        this.player = player;
        this.setRotateToCenter();
        this.setLabel(jslLabel.ZOMBIE);
        this.strange = 10.0f;
    }

    public void update(float et) {
        jslVector2 v = new jslVector2(player.getCenterX() - getCenterX(), player.getCenterY() - getCenterY());
        v.normalize();
        v.multiply(vel);
        setVel(v);

        float theta = (float)Math.atan2(v.x, v.y);
        setRotate(2*(float)Math.PI - theta);
    }

    public void afterUpdate(float et) {
        if(!isReady) {
            timer -= et;

            if(timer <= 0) {
                timer = 1.0f / hitRate;
                isReady = true;
            }
        }else {
            float dx = getCenterX() - player.getCenterX();
            float dy = getCenterY() - player.getCenterY();

            if (Math.abs(dx) <= (player.getW() + getW()) * 0.6f) {
                if (Math.abs(dy) <= (player.getH() + getH()) * 0.5f) {
                    HUD.addHp(-strange);
                    isReady = false;
                }
            }
        }
    }

    public void onCollision(jslObject other) {
        if(other.getLabel() != jslLabel.BULLET) {
            collisionBox.bound(other);
        }
    }

    public void render(Graphics g) {
        g.drawImage(texture, (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}
