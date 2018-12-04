package MainFiles;

import jslEngine.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends jslObject {

    private BufferedImage texture = Texture.playerImg;
    private CollisionBox collisionBox;
    private jslManager jsl;

    public Player(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);
        this.setLabel(jslLabel.PLAYER);
        this.setRotateToCenter();
        this.jsl = jsl;
        this.collisionBox = new CollisionBox(this) {
            @Override
            public void onCollision(jslObject other) {
                switch (other.getLabel()) {
                    case WALL:
                    case ZOMBIE:
                        bound(other);
                        break;
                }
            }
        };
    }

    public void update(float et) {
        move(et);
        collisionBox.collision(jsl);
    }

    public void onCollision(jslObject other) {
        if(!other.is(jslLabel.BULLET)) {
            if(!other.is(jslLabel.ITEM)) {
                collisionBox.bound(other);
            }
        }
    }

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
