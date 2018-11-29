package MainFiles;

import jslEngine.*;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class MainClass extends jslEngine {

    public static int WW = 1000, WH = 600;

    private Player player;
    private Shotgun shotgun;
    private Camera camera;


    private MainClass() {
        start("Zombies", WW, WH);
    }

    protected void onCreate() {
        WW = WW();
        WH = WH();
        player = new Player(WW() * 0.5f, WH() * 0.5f, 32, 32);
        jsl.add(player);
        jsl.add(new PlayerController(player, 350.0f));

        camera = new Camera(player);
        shotgun = new Shotgun(player);
    }

    protected void update(float et) {
        camera.update(et);
        jsl.setTranslate(-camera.getX(), -camera.getY());

        shotgun.update(et);
    }

    protected void render(Graphics g) {
        shotgun.render(g);
    }

    protected void onMouseMoved(MouseEvent e) {
        shotgun.onMove(e);
    }

    protected void onMouseDragged(MouseEvent e) {
        shotgun.onMove(e);
    }

    public static void main(String[] args) {
        new MainClass();
    }
}
