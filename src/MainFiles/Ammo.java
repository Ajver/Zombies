package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;

import java.awt.*;

public class Ammo extends jslObject {

    private jslManager jsl;

    public Ammo(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);
        this.jsl = jsl;
        this.setLabel(jslLabel.ITEM);
        this.collisionBox.setHasBounds(false);
    }

    public void onCollision(jslObject other) {
        if(other.is(jslLabel.PLAYER)) {
            HUD.addMagazine(2);
            jsl.removeObject(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(192, 192, 192));
        g.fillRect((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}
