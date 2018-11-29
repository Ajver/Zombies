package MainFiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {

    public static BufferedImage playerImg = null;
    public static BufferedImage zombieImg = null;
    public static BufferedImage blockImg = null;

    public Texture() { }

    public static boolean loadTextures() {
        try {
            playerImg = ImageIO.read(new File("res/img/Player.png"));
            zombieImg = ImageIO.read(new File("res/img/Zombie.png"));
            blockImg = ImageIO.read(new File("res/img/Block.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
