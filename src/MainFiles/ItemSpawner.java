package MainFiles;

import jslEngine.jslManager;
import jslEngine.jslTimer;

import java.util.Random;

public class ItemSpawner {

    private jslTimer nextAmmoTimer;
    private jslManager jsl;

    public ItemSpawner(jslManager jsl) {
        this.jsl = jsl;

        nextAmmoTimer = new jslTimer(10);
        nextAmmoTimer.start();
    }

    public void update(float et) {
        if(nextAmmoTimer.update(et)) {
            if(Ammo.getCounter() < 2) {
                Random r = new Random();
                jsl.add(new Ammo(r.nextInt(1000), r.nextInt(1000), 32, 32, jsl));
            }
        }
    }
}
