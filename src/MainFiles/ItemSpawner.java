package MainFiles;

import jslEngine.jslManager;
import jslEngine.jslTimer;

import java.util.Random;

public class ItemSpawner {

    private jslTimer nextAmmoTimer;
    private jslTimer nextHealthTimer;
    private jslManager jsl;

    public ItemSpawner(jslManager jsl) {
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
                jsl.add(new Ammo(r.nextInt(800), r.nextInt(800), 32, 32, jsl));
            }
        }
        if(nextHealthTimer.update(et)) {
            if(Health.getCounter() < 1) {
                Random r = new Random();
                jsl.add(new Health(r.nextInt(800), r.nextInt(800), 32, 32, jsl));
            }
        }
    }
}
