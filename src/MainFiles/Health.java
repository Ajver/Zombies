package MainFiles;

import jslEngine.jslManager;
import jslEngine.jslSound;

import java.awt.*;

public class Health extends Item {

    public Health(float x, float y, float w, float h, jslManager jsl, ItemSpawner spawner) {
        super(x, y, w, h, jsl, spawner);
        image = Texture.healthImg;
        pickupSound = new jslSound("res/sounds/pop.wav");
        pickupSound.setLevel(0.8f);
    }

    protected void onPick() {
        pickupSound.play();
        HUD.addHp(20);
    }

    public void render(Graphics g) {
        g.drawImage(image, (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}

