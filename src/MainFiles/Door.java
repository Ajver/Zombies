package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslObject;

import java.awt.*;

public class Door extends jslObject {

    public Door(float x, float y, float w, float h) {
        super(x, y, w, h);
        setLabel(jslLabel.DOOR);
    }

    public void update(float et) {

    }

    public void render(Graphics g) {

    }
}
