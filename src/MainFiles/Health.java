package MainFiles;

import jslEngine.jslManager;

import java.awt.*;

public class Health extends Item {

    public Health(float x, float y, float w, float h, jslManager jsl, ItemSpawner spawner) {
        super(x, y, w, h, jsl, spawner);
    }

    protected void onPick() {
        HUD.addHp(20);
    }

    public void render(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        g.fillOval((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}

