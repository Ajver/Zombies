package MainFiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {

    public static BufferedImage playerImg = null;
    public static BufferedImage zombieImg = null;
    public static BufferedImage background = null, ceil = null;

    public Texture() { }

    public static boolean loadTextures() {
        long start = System.currentTimeMillis();
        try {
            playerImg = ImageIO.read(new File("res/img/Player.png"));
            zombieImg = ImageIO.read(new File("res/img/Zombie.png"));

            background = ImageIO.read(new File("res/map/Background.png"));
            ceil = ImageIO.read(new File("res/map/Ceil.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        long stop = System.currentTimeMillis();
        System.out.println("Loading graphics time: " + (stop-start));

        return true;
    }
}
