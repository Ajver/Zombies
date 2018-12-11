package MainFiles;

import Objects.ItemSpawner;
import Objects.Player;
import Objects.Zombie;
import Objects.ZombieSpawner;
import jslEngine.*;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainClass extends jslEngine {

    public static int WW = 1000, WH = 600;
    public static float blockSize;
    public static float creatureSize;

    private Player player;
    private Shotgun shotgun;
    private Map map;
    private LevelManager manager;

    private ArrayList<ZombieSpawner> zombieSpawners = new ArrayList<>();
    private ArrayList<ItemSpawner> itemSpawners = new ArrayList<>();

    private jslSound themeMusic;

    private boolean specialData = false;

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
                jslLabel.ITEM,
                jslLabel.BULLET,
                jslLabel.PLAYER,
                jslLabel.ZOMBIE,
                jslLabel.ACID_BULLET,
                jslLabel.WALL,
                jslLabel.DOOR,
                jslLabel.ZOMBIE_HP,
                jslLabel.DEFAULT);

        WW = WW();
        WH = WH();

        blockSize = 64;
        creatureSize = 64;

        map = new Map(jsl, zombieSpawners, itemSpawners);

        player = (Player)jsl.getObject(jslLabel.PLAYER);
        jsl.add(new PlayerController(player, 350.0f));

        Camera.focus(player);
        shotgun = new Shotgun(player, jsl);

        manager = new LevelManager(zombieSpawners, jsl);

        jslCursor.setCursor("res/img/Crosshair.png");

        themeMusic = new jslSound("res/sounds/theme.wav");
        themeMusic.setLevel(0.7f);
        themeMusic.loop();
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
            case END:
                // Stop item spawners (and remove the items)
                for(int i=0; i<itemSpawners.size(); i++) {
                    itemSpawners.get(i).stop();
                }
                jsl.removeObject(jslLabel.ITEM);

                // Show arrow

                break;
        }
    }

    protected void beforeRender(Graphics g) {
        // Clear the screen
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, WW(), WH());

        // Render background image
        map.render(g);
    }

    protected void render(Graphics g) {
        map.renderCeil(g);
        HUD.render(g);

        g.setColor(new Color(255, 255, 255));

        if(manager.getLevel() != 0) {
            g.drawString("Level: " + manager.getLevel(), 40, 50);
        }

        if (specialData) {
            g.drawString("State: " + manager.getState(), 40, 100);
            g.drawString("FPS: " + getFpsCount(), 40, 130);
        }
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

        switch (e.getKeyCode()) {
            case KeyEvent.VK_P:
                specialData = !specialData;
                break;
        }
    }

    public static void main(String[] args) {
        new MainClass();
    }
}
