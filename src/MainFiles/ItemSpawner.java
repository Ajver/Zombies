package MainFiles;

import jslEngine.jslManager;
import jslEngine.jslObject;
import jslEngine.jslTimer;

import java.awt.*;
import java.util.Random;

public class ItemSpawner extends jslObject {

    private jslTimer nextAmmoTimer;
    private jslTimer nextHealthTimer;
    private jslManager jsl;

    public ItemSpawner(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);

        this.jsl = jsl;

        nextAmmoTimer = new jslTimer(8);
        nextAmmoTimer.start();

        nextHealthTimer = new jslTimer(15);
        nextHealthTimer.start();
    }

    public void update(float et) {
        if(nextAmmoTimer.update(et)) {
            if(Ammo.getCounter() < 2) {
                Random r = new Random();
                jsl.add(new Ammo(getX(), getY(), 32, 32, jsl));
            }
        }
        if(nextHealthTimer.update(et)) {
            if(Health.getCounter() < 1) {
                Random r = new Random();
                jsl.add(new Health(r.nextInt(800), r.nextInt(800), 32, 32, jsl));
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(128, 128, 128));
        g.fillOval((int)getX(), (int)getY(), (int)getW(), (int)getH());
    }
}
