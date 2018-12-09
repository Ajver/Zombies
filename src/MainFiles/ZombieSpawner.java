package MainFiles;

import jslEngine.jslManager;
import jslEngine.jslObject;
import jslEngine.jslTimer;

import java.util.Random;

public class ZombieSpawner {

    private jslManager jsl;
    private jslTimer nextZombieTimer;

    private float x, y;

    private Random r = new Random();

    public ZombieSpawner(float x, float y, jslManager jsl) {
        this.x = x;
        this.y = y;
        this.jsl = jsl;

        nextZombieTimer = new jslTimer(r.nextInt(4) + 1.0f);
        nextZombieTimer.start();
    }

    public void update() {
        if (nextZombieTimer.update()) {
            Zombie.newZombie(jsl, x, y);
            restart();
        }
    }

    public void restart() {
        nextZombieTimer.setDuration(r.nextInt(3) + 3.0f);
        nextZombieTimer.restart();
    }

}
