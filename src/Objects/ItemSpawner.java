package Objects;

import MainFiles.Texture;
import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;
import jslEngine.jslTimer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ItemSpawner extends jslObject {

    private BufferedImage image = Texture.itemSpawner;

    private jslManager jsl;
    private boolean hasItem = false;
    private jslTimer nextItemTimer;
    private Random r = new Random();

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
            float w = getW() * 0.5f;
            float h = getW() * 0.5f;
            float x = getCenterX() - w * 0.5f;
            float y = getCenterY() - h * 0.5f;
            if(ran < 65) {
                jsl.add(new Ammo(x, y, w, h, jsl, this));
            }else {
                jsl.add(new Health(x, y, w, h, jsl, this));
            }
            hasItem = true;
            nextItemTimer.setDuration(r.nextInt(5) + 10);
        }
    }

    public void clear() {
        hasItem = false;
        nextItemTimer.restart();
    }

    public void stop() {
        nextItemTimer.stop();
    }

    public void render(Graphics g) {
        g.drawImage(image, (int)getX(), (int)getY(), null);
    }
}
