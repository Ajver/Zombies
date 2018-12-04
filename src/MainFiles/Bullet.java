package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;

import java.awt.*;

public class Bullet extends jslObject {

    private jslManager jsl;
    private CollisionBox collisionBox;

    public Bullet(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);
        this.setLabel(jslLabel.BULLET);
        this.jsl = jsl;
        this.collisionBox = new CollisionBox(this) {
            @Override
            public void onCollision(jslObject other) {
                if(other.is(jslLabel.WALL)) {
                    jsl.removeObject(o);
                }
            }
        };
    }

    public void update(float et) {
        move(et);
        collisionBox.collision(jsl);
    }

    public void onCollision(jslObject other) {
        if(other.is(jslLabel.WALL)) {
            jsl.removeObject(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(0,0,0));
        g.fillOval((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}
