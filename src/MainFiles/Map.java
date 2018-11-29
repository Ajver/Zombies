package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;

import java.awt.*;

public class Map {

    int w = 40, h = 25;

    public Map(jslManager jsl) {
        float bs = 32;

        // Horizontal walls
        for(int i=0; i<w; i++) {
            Block block = new Block(i*bs, 0, bs, bs, jslLabel.WALL);
            jsl.add(block);

            block = new Block(i*bs, h*bs, bs, bs, jslLabel.WALL);
            jsl.add(block);
        }

        // Vertical walls
        for(int i=1; i<h; i++) {
            Block block = new Block(0, i*bs, bs, bs, jslLabel.WALL);
            jsl.add(block);

            block = new Block((w-1)*bs, i*bs, bs, bs, jslLabel.WALL);
            jsl.add(block);
        }
    }

    public void render(Graphics g) {

    }
}
