package Objects;

import jslEngine.jslManager;
import jslEngine.jslObject;

import java.awt.*;

public class CollisionBox {

    protected jslObject o;

    public CollisionBox(jslObject o) {
        this.o = o;
    }

    public void collision(jslManager jsl) {
        // Collision
        for(int i=0; i<jsl.getObjects().size(); i++) {
            jslObject other = jsl.getObject(i);
            if(other != o) {
                if(isCollision(other)) {
                    onCollision(other);
                }
            }
        }
    }

    public boolean isCollision(jslObject other) {
        Rectangle otherBounds = getBounds(other);
        Rectangle bounds = getBounds();
        return bounds.intersects(otherBounds);
    }

    public void bound(jslObject other) {
        float toLeft = o.getX() - (other.getX() - o.getW());
        float toRight = (other.getX() + other.getW()) - o.getX();
        float toUp = o.getY() - (other.getY() - o.getH());
        float toDown = (other.getY() + other.getH()) - o.getY();

        if(toLeft < toRight) {
            if(toLeft < toUp) {
                if(toLeft < toDown) {
                    o.move(-toLeft, 0);
                }else {
                    o.move(0, toDown);
                }
            }else {
                if(toUp < toDown) {
                    o.move( 0, -toUp);
                }else {
                    o.move(0, toDown);
                }
            }
        }else {
            if(toRight < toUp) {
                if(toRight < toDown) {
                    o.move(toRight, 0);
                }else {
                    o.move(0, toDown);
                }
            }else {
                if(toUp < toDown) {
                    o.move( 0, -toUp);
                }else {
                    o.move(0, toDown);
                }
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)o.getX(), (int)o.getY(), (int)o.getW(), (int)o.getH());
    }
    public Rectangle getBounds(jslObject o) {
        return new Rectangle((int)o.getX(), (int)o.getY(), (int)o.getW(), (int)o.getH());
    }

    public void onCollision(jslObject other) {}
}
