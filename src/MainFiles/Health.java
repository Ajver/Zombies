package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;

import java.awt.*;

public class Health extends jslObject {

    private static int counter = 0;
    private jslManager jsl;

    public Health(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);
        this.jsl = jsl;
        this.setLabel(jslLabel.ITEM);
        this.collisionBox.setHasBounds(false);

        counter++;
    }

    public void onCollision(jslObject other) {
        if(other.is(jslLabel.PLAYER)) {
            HUD.addHp(20);
            counter--;
            jsl.removeObject(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        g.fillOval((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }

    public static int getCounter() { return counter; }
}

