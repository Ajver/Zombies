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

    private float x, y, w, h;

    private BufferedImage background = Texture.background;
    private BufferedImage ceil = Texture.ceil;

    public Map(jslManager jsl) {
        loadMap("res/map/Map.png", jsl);
    }

    public boolean loadMap(String path, jslManager jsl) {
        try {
            BufferedImage mapImg = ImageIO.read(new File(path));

            int w = mapImg.getWidth();
            int h = mapImg.getHeight();

            for(int yy=0; yy<h; yy++) {
                for(int xx=0; xx<w; xx++) {
                    jslObject o = null;

                    int rgb = mapImg.getRGB(xx, yy);
                    int r = (rgb >> 16) & 0xff;
                    int g = (rgb >> 8) & 0xff;
                    int b = (rgb) & 0xff;

                    float x = xx * MainClass.blockSize;
                    float y = yy * MainClass.blockSize;

                    if(r == 255 && g == 255 && b == 255) {
                        o = new Wall(x, y, MainClass.blockSize, MainClass.blockSize);
                    }else if(r == 0 && g == 0 && b == 255){
                        o = new Player(x, y, MainClass.creatureSize, MainClass.creatureSize, jsl);
                    }else if(r == 0 && g == 255 && b == 0) {
                        o = new ItemSpawner(x, y, MainClass.creatureSize, MainClass.creatureSize, jsl);
                    }else if(r == 255 && g == 0 && b == 0) {
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

    public void update(float et) {
        x = Math.min(Math.max(0, Camera.getX()), background.getWidth()-1);
        y = Math.min(Math.max(0, Camera.getY()), background.getHeight()-1);
        w = Math.max(Math.min(MainClass.WW, background.getWidth()-x), 1);
        h = Math.max(Math.min(MainClass.WH, background.getHeight()-y), 1);
    }

    public void render(Graphics g) {
        drawBG(background, g);
    }

    public void renderCeil(Graphics g) {
        drawBG(ceil, g);
    }

    private void drawBG(BufferedImage img, Graphics g) {
        g.drawImage(img.getSubimage((int)x, (int)y, (int)w, (int)h),
                -(int)Math.min(0, Camera.getX()),
                -(int)Math.min(0, Camera.getY()),
                null);
    }
}
