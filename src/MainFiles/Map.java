package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Map {

    int w = 40, h = 25;

    public Map(jslManager jsl) {
        float bs = 32;

//        // Horizontal walls
//        for(int i=0; i<w; i++) {
//            Block block = new Block(i*bs, 0, bs, bs, jslLabel.WALL);
//            jsl.add(block);
//
//            block = new Block(i*bs, h*bs, bs, bs, jslLabel.WALL);
//            jsl.add(block);
//        }
//
//        // Vertical walls
//        for(int i=1; i<h; i++) {
//            Block block = new Block(0, i*bs, bs, bs, jslLabel.WALL);
//            jsl.add(block);
//
//            block = new Block((w-1)*bs, i*bs, bs, bs, jslLabel.WALL);
//            jsl.add(block);
//        }

        loadMap("res/map/Map1.png", jsl);
    }

    public boolean loadMap(String path, jslManager jsl) {
        try {
            BufferedImage mapImg = ImageIO.read(new File(path));

            this.w = mapImg.getWidth();
            this.h = mapImg.getHeight();

            for(int yy=0; yy<h; yy++) {
                for(int xx=0; xx<w; xx++) {
                    jslObject o = null;

                    int rgb = mapImg.getRGB(xx, yy);
                    int r = (rgb >> 16) & 0xff;
                    int g = (rgb >> 8) & 0xff;
                    int b = (rgb) & 0xff;

                    float x = xx * MainClass.blockSize - xx;
                    float y = yy * MainClass.blockSize - yy;

                    if(r == 255 && g == 255 && b == 255) {
                        o = new Block(x, y, MainClass.blockSize, MainClass.blockSize, jslLabel.WALL);
                    }else if(r == 0 && g == 0 && b == 255){
                        o = new Player(x, y, MainClass.creatureSize, MainClass.creatureSize);
                    }else if(r == 0 && g == 255 && b == 0) {
                        o = new ItemSpawner(x, y, MainClass.creatureSize, MainClass.creatureSize, jsl);
                    }else if(r == 255 && g == 0 && b == 0) {
                        // Zombie spawner
                        o = new ZombieSpawner(x, y, MainClass.creatureSize, MainClass.creatureSize, jsl);
                    }else if(r == 255 && g == 255 && b == 0) {
                        // Door
                    }else if(r == 153 && g == 153 && b == 0) {
                        // Player door
                    }

                    if(o != null) {
                        jsl.add(o);
                    }
                }
            }

            return true;
        } catch (IOException e) {
            System.out.println("Could not find file: " + path);
            e.printStackTrace();
            return false;
        }
    }

    public void render(Graphics g) {

    }
}
