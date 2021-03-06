package MainFiles;

import Objects.ItemSpawner;
import Objects.Player;
import Objects.Wall;
import Objects.ZombieSpawner;
import jslEngine.jslManager;
import jslEngine.jslObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Map {

    private float x, y, w, h;

    private BufferedImage background = Texture.background;
    private BufferedImage ceil = Texture.ceil;

    public Map(jslManager jsl, ArrayList<ZombieSpawner> zombieSpawners, ArrayList<ItemSpawner> itemSpawners) {
        loadMap("res/map/Map.png", jsl, zombieSpawners, itemSpawners);
    }

    public boolean loadMap(String path, jslManager jsl, ArrayList<ZombieSpawner> zombieSpawners, ArrayList<ItemSpawner> itemSpawners) {
        try {
            BufferedImage mapImg = ImageIO.read(new File(path));

            int w = mapImg.getWidth();
            int h = mapImg.getHeight();

            boolean[][] checkedMap = new boolean[w][h];

            for(int yy=0; yy<h; yy++) {
                for(int xx=0; xx<w; xx++) {
                    if(checkedMap[xx][yy]) {
                        continue;
                    }else {
                        checkedMap[xx][yy] = true;
                    }

                    jslObject o = null;

                    int rgb = mapImg.getRGB(xx, yy);
                    int r = (rgb >> 16) & 0xff;
                    int g = (rgb >> 8) & 0xff;
                    int b = (rgb) & 0xff;

                    float x = xx * MainClass.blockSize;
                    float y = yy * MainClass.blockSize;

                    if(r == 255 && g == 255 && b == 255) {
                        boolean isHorizontalLine = false;
                        float wallW = MainClass.blockSize;
                        float wallH = MainClass.blockSize;
                        for(int i=xx+1; i<w; i++) {
                            int rgb2 = mapImg.getRGB(i, yy);
                            if (rgb == rgb2 && !checkedMap[i][yy]) {
                                checkedMap[i][yy] = true;
                                isHorizontalLine = true;
                                wallW += MainClass.blockSize;
                            }else {
                                break;
                            }
                        }
                        if(!isHorizontalLine) {
                            for (int i = yy + 1; i < h; i++) {
                                int rgb2 = mapImg.getRGB(xx, i);
                                if (rgb == rgb2 && !checkedMap[xx][i]) {
                                    checkedMap[xx][i] = true;
                                    wallH += MainClass.blockSize;
                                }else {
                                    break;
                                }
                            }
                        }
                        o = new Wall(x, y, wallW, wallH);
                    }else if(r == 0 && g == 0 && b == 255){
                        o = new Player(x, y, MainClass.creatureSize, MainClass.creatureSize, jsl);
                    }else if(r == 0 && g == 255 && b == 0) {
                        o = new ItemSpawner(x, y, MainClass.creatureSize, MainClass.creatureSize, jsl);
                        itemSpawners.add((ItemSpawner)o);
                    }else if(r == 255 && g == 0 && b == 0) {
                        zombieSpawners.add(new ZombieSpawner(x, y, jsl));
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
