package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;
import jslEngine.jslTimer;

import java.awt.*;
import java.util.Random;

public class ItemSpawner extends jslObject {

    private jslTimer nextItemTimer;
    private jslManager jsl;
    private Random r = new Random();
    private boolean hasItem = false;

    public ItemSpawner(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);
        this.jsl = jsl;

        this.setLabel(jslLabel.SPAWNER);

        nextItemTimer = new jslTimer(r.nextInt(5) + 10);
        nextItemTimer.start();
    }

    public void update(float et) {
        if(hasItem) {
            return;
        }

        if(nextItemTimer.update()) {
            Random r = new Random();
            int ran = r.nextInt(100);
            if(ran < 65) {
                jsl.add(new Ammo(getX(), getY(), 32, 32, jsl, this));
            }else {
                jsl.add(new Health(getX(), getY(), 32, 32, jsl, this));
            }
            hasItem = true;
            nextItemTimer.setDuration(r.nextInt(5) + 10);
        }
    }

    public void clear() {
        this.hasItem = false;
        this.nextItemTimer.restart();
    }

    public void render(Graphics g) {
        g.setColor(new Color(128, 128, 128));
        g.fillOval((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}
