package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;

import java.awt.*;

public class Ammo extends jslObject {

    private static int counter = 0;
    private jslManager jsl;

    public Ammo(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);
        this.jsl = jsl;
        this.setLabel(jslLabel.AMMO);
        this.collisionBox.setHasBounds(false);

        counter++;
    }

    public void onCollision(jslObject other) {
        if(other.is(jslLabel.PLAYER)) {
            HUD.addMagazine(2);
            counter--;
            jsl.removeObject(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(96, 0, 0));
        g.fillRect((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }

    public static int getCounter() { return counter; }
}
