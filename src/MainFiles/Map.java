package MainFiles;

import jslEngine.jslManager;

import java.awt.*;

public class Map {

    int w = 40, h = 25;

    public Map(jslManager jsl) {
        float bs = 32;

        // Horizontal walls
        for(int i=0; i<w; i++) {
            jsl.add(new Block(i*bs, 0, bs, bs));
            jsl.add(new Block(i*bs, h*bs, bs, bs));
        }

        // Vertical walls
        for(int i=1; i<h; i++) {
            jsl.add(new Block(0, i*bs, bs, bs));
            jsl.add(new Block((w-1)*bs, i*bs, bs, bs));
        }
    }

    public void render(Graphics g) {

    }
}
