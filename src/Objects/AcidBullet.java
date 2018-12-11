package Objects;

import MainFiles.HUD;
import MainFiles.MainClass;
import MainFiles.Texture;
import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AcidBullet extends jslObject {

    private BufferedImage texture = Texture.healthImg;

    private CollisionBox collisionBox;
    private jslManager jsl;

    public AcidBullet(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);
        this.jsl = jsl;
        setLabel(jslLabel.ACID_BULLET);

        collisionBox = new CollisionBox(this) {
            @Override
            public void onCollision(jslObject other) {
                switch (other.getLabel()) {
                    case PLAYER:
                        HUD.addHp(-25.0f);

                    case WALL:
                        jsl.removeObject(o);
                        break;
                }
            }
        };
    }

    public void update(float et) {
        move(et);
        collisionBox.collision(jsl);
    }

    public void render(Graphics g) {
        ((Graphics2D)g).rotate(getRotate(), getX() + getRotateX(), getY() + getRotateY());

        g.drawImage(texture, (int)getX(), (int)getY(), (int)getW(), (int)getH(),null);

        ((Graphics2D)g).rotate(-getRotate(), getX() + getRotateX(), getY() + getRotateY());
    }
}
