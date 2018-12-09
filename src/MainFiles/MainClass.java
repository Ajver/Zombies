package MainFiles;

import jslEngine.*;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MainClass extends jslEngine {

    public static int WW = 1000, WH = 600;
    public static float blockSize;
    public static float creatureSize;

    private Player player;
    private Shotgun shotgun;
    private Map map;
    private LevelManager manager;

    private ArrayList<ZombieSpawner> zombieSpawners = new ArrayList<>();

    private jslSound themeMusic;

    private MainClass() {
        start("Zombies", WindowType.FULLSCREEN);
//        start("Zombies", WW, WH);
        setAntialiasing(true);

        jslSound pickupSound = new jslSound("res/sounds/pickupAmmo.wav");
        pickupSound.setLevel(0.7f);
        pickupSound.play();
    }

    public void onCreate() {
        Texture.loadTextures();

        WW = WW();
        WH = WH();

        HUD.reset();

        jsl.setAutoClearScreen(false);
        jsl.setRenderOrder(
                jslLabel.SPAWNER,
                jslLabel.GROUND,
                jslLabel.BULLET,
                jslLabel.ITEM,
                jslLabel.PLAYER,
                jslLabel.ZOMBIE,
                jslLabel.WALL,
                jslLabel.DOOR,
                jslLabel.ZOMBIE_HP,
                jslLabel.DEFAULT);

        WW = WW();
        WH = WH();

        blockSize = 64;
        creatureSize = 64;

        map = new Map(jsl, zombieSpawners);

        player = (Player)jsl.getObject(jslLabel.PLAYER);
        jsl.add(new PlayerController(player, 350.0f));

        Camera.focus(player);
        shotgun = new Shotgun(player, jsl);

        manager = new LevelManager(1.0f, 1.0f, jsl);

        themeMusic = new jslSound("res/sounds/theme.wav");
        themeMusic.setLevel(0.7f);
        themeMusic.loop();
        jslCursor.setCursor("res/img/Crosshair.png");
    }

    protected void update(float et) {
        Camera.update(et);
        jsl.setTranslate(-Camera.getX(), -Camera.getY());

        for(int i=0; i<zombieSpawners.size(); i++) {
            zombieSpawners.get(i).update();
        }

        shotgun.update(et);
        HUD.update(et);
        map.update(et);


        switch (manager.update()) {
            case GAME:
                // Restart zombie spawners timers
                for(int i=0; i<zombieSpawners.size(); i++) {
                    zombieSpawners.get(i).restart();
                }
                break;
            case PAUSE:

                break;
            case LEAVE:
                // Start leave animation
                break;
            case END:
                // End game
                break;
        }
    }

    protected void beforeRender(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WW(), WH());
        map.render(g);
    }

    protected void render(Graphics g) {
        map.renderCeil(g);

        HUD.render(g);

        g.setColor(new Color(255, 255,255));
        g.drawString("FPS: " + getFpsCount(), 20, 50);
        g.drawString("Level: " + manager.getLevel(), 20, 100);
        g.drawString("State: " + manager.getState(), 20, 130);
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
