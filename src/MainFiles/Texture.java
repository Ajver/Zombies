package MainFiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {

    public static BufferedImage playerImg = null;
    public static BufferedImage zombieImg = null;
    public static BufferedImage itemSpawner = null;

    public static BufferedImage ammoImg = null;
    public static BufferedImage healthImg = null;

    public static BufferedImage background = null;
    public static BufferedImage ceil = null;

    public static BufferedImage[] stains = new BufferedImage[4];

    public Texture() { }

    public static boolean loadTextures() {
        long start = System.currentTimeMillis();

        BufferedImage defaultImage = new BufferedImage(32, 32, BufferedImage.TYPE_3BYTE_BGR);

        for(int i=0; i<defaultImage.getWidth(); i++) {
            for(int j=0; j<defaultImage.getWidth(); j++) {
                defaultImage.setRGB(j, i, 0xE07DD4);
            }
        }

        playerImg = zombieImg = itemSpawner = ammoImg = healthImg = background = ceil = defaultImage;
        for(int i=1; i<=4; i++) {
            stains[i-1] = defaultImage;
        }

        try {
            playerImg = ImageIO.read(new File("res/img/Player.png"));
            zombieImg = ImageIO.read(new File("res/img/Zombie.png"));
            itemSpawner = ImageIO.read(new File("res/img/ItemSpawner.png"));

            ammoImg = ImageIO.read(new File("res/img/Ammo.png"));
            healthImg = ImageIO.read(new File("res/img/Heart.png"));

            background = ImageIO.read(new File("res/map/Background.jpg"));
            ceil = ImageIO.read(new File("res/map/Ceil.png"));

            for(int i=1; i<=4; i++) {
                stains[i-1] = ImageIO.read(new File("res/img/stain" + i + ".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        long stop = System.currentTimeMillis();
        System.out.println("Loading graphics time: " + (stop-start));

        return true;
    }
}
