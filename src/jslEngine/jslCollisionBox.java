package jslEngine;

import java.awt.*;

public class jslCollisionBox {

    private jslObject o;
    private Rectangle selfBounds = null;

    public jslCollisionBox(jslObject o) {
        this.o = o;
    }

    public void collision(jslObject other) {
        Rectangle otherBounds = other.collisionBox.getBounds();
        Rectangle bounds = selfBounds == null ? getBounds() : selfBounds;
        if(bounds.intersects(otherBounds)) {
            o.onCollision(other);
        }
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

    public void setSelfBounds(Rectangle bounds) { this.selfBounds = bounds; }
}
