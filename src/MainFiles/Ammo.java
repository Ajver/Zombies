package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;

import java.awt.*;

public class Ammo extends Item {

    public Ammo(float x, float y, float w, float h, jslManager jsl, ItemSpawner spawner) {
        super(x, y, w, h, jsl, spawner);
    }

    protected void onPick() {
        HUD.addMagazine(2);
    }


    public void render(Graphics g) {
        g.setColor(new Color(192, 192, 192));
        g.fillRect((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}
