package MainFiles;

import jslEngine.jslManager;
import jslEngine.jslObject;
import jslEngine.jslTimer;

import java.awt.*;
import java.util.Random;

public class ZombieSpawner extends jslObject {

    private jslManager jsl;
    private jslTimer nextZombieTimer;

    private Random r = new Random();

    public ZombieSpawner(float x, float y, float w, float h, jslManager jsl) {
        super(x, y, w, h);
        this.jsl = jsl;

        nextZombieTimer = new jslTimer(r.nextInt(6) + 4.0f);
        nextZombieTimer.start();
    }

    public void update(float et) {
        if (nextZombieTimer.update()) {
            Zombie.newZombie(jsl, getX(), getY());
            nextZombieTimer.setDuration(r.nextInt(6) + 5.0f);
        }
    }
}
