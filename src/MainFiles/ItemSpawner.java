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

    public ItemSpawner(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);
        this.jsl = jsl;

        this.setLabel(jslLabel.SPAWNER);
        this.collisionBox.setHasBounds(false);

        nextItemTimer = new jslTimer(r.nextInt(5) + 10);
        nextItemTimer.start();
    }

    public void update(float et) {
        if(nextItemTimer.update()) {
            Random r = new Random();
            switch (r.nextInt(3)) {
                case 0:
                case 1:
                    jsl.add(new Ammo(getX(), getY(), 32, 32, jsl));
                    break;
                case 2:
                    jsl.add(new Health(getX(), getY(), 32, 32, jsl));
                    break;
            }
            nextItemTimer.setDuration(r.nextInt(5) + 10);
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(128, 128, 128));
        g.fillOval((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}
