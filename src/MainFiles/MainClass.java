package MainFiles;

import jslEngine.*;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MainClass extends jslEngine {

    public static int WW = 1000, WH = 600;

    private Player player;
    private Shotgun shotgun;
    private Camera camera;
    private Map map;

    public static Texture tex;

    private MainClass() {
        start("Zombies", WindowType.jslFullscreen);
    }

    protected void onCreate() {
        (new Texture()).loadTextures();

        WW = WW();
        WH = WH();
        player = new Player(WW() * 0.5f, WH() * 0.5f, 32, 32);
        jsl.add(player);
        jsl.add(new PlayerController(player, 350.0f));

        camera = new Camera(player);
        shotgun = new Shotgun(player, jsl);
        map = new Map(jsl);

        Random r = new Random();
        for(int i=0; i<3; i++) {
            jsl.add(new Zombie(r.nextInt(500), r.nextInt(500), 32, 32, 100.0f, player));
        }
    }

    protected void update(float et) {
        camera.update(et);
        jsl.setTranslate(-camera.getX(), -camera.getY());

        shotgun.update(et);
    }

    protected void render(Graphics g) {
        shotgun.render(g);
    }

    protected void onMousePressed(MouseEvent e) {
        shotgun.onPress(e);
    }
    protected void onMouseReleased(MouseEvent e) {
        shotgun.onRelease(e);
    }
    protected void onMouseMoved(MouseEvent e) {
        shotgun.onMove(e);
    }
    protected void onMouseDragged(MouseEvent e) {
        shotgun.onMove(e);
    }

    protected void onKeyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(144);
        }
    }

    public static void main(String[] args) {
        new MainClass();
    }
}
