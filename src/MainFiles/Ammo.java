package MainFiles;

import jslEngine.jslManager;
import jslEngine.jslSound;

import java.awt.*;

public class Ammo extends Item {

    public Ammo(float x, float y, float w, float h, jslManager jsl, ItemSpawner spawner) {
        super(x, y, w, h, jsl, spawner);
        image = Texture.ammoImg;
        pickupSound = new jslSound("res/sounds/pickupAmmo.wav");
        pickupSound.setLevel(0.7f);
    }

    protected void onPick() {
        pickupSound.play();
        HUD.addMagazine(2);
    }


    public void render(Graphics g) {
        g.drawImage(image, (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}
