package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslObject;
import jslEngine.jslTimer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Stain extends jslObject {

    private BufferedImage[] images = Texture.stains;
    private jslTimer timer;
    private int step = 0;

    public Stain(float x, float y, float w, float h) {
        super(x, y, w, h);;
        setLabel(jslLabel.GROUND);
        timer = new jslTimer(0.15f);
    }

    public void update(float et) {
        if(timer.update()) {
            step = images.length-1;
            timer.stop();
        }else {
            step = Math.min((int)(timer.getProgress() * images.length), images.length-1);
        }
    }

    public void render(Graphics g) {
        ((Graphics2D)g).rotate(getRotate(), getX() + getRotateX(), getY() + getRotateY());

        g.drawImage(images[step], (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);

        ((Graphics2D)g).rotate(-getRotate(), getX() + getRotateX(), getY() + getRotateY());
    }
}
