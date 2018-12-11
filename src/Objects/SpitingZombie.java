package Objects;

import MainFiles.Texture;
import jslEngine.jslManager;
import jslEngine.jslTimer;
import jslEngine.jslVector2;

public class SpitingZombie extends Zombie {

    private jslTimer spitTimer;

    public SpitingZombie(float w, float h, jslManager jsl) {
        super(w, h, jsl);
        texture = Texture.spitingZombieImg;
        spitTimer = new jslTimer(r.nextInt(5) + 5.0f);
        spitTimer.start();
    }

    public void update(float et) {
        super.update(et);

        // Spiting
        if(spitTimer.update()) {
            spitTimer.setDuration((r.nextInt(5) + 5.0f));
            spitTimer.restart();

            float dx = player.getCenterX() - getCenterX();
            float dy = player.getCenterY() - getCenterY();
            jslVector2 v = new jslVector2(dx, dy);
            v.normalize();
            v.multiply(800.0f);

            AcidBullet bullet = new AcidBullet(getX(), getY(), 32, 32, jsl);
            bullet.setVel(v.x, v.y);
            jsl.add(bullet);
        }
    }
}
