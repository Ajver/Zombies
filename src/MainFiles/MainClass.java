package MainFiles;

import jslEngine.*;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class MainClass extends jslEngine {

    private Player player;

    private MainClass() {
        start("Zombies", 600, 400);
    }

    protected void onCreate() {
        player = new Player(WW() * 0.5f, WH() * 0.5f, 32, 32);
        jsl.add(player);
        jsl.add(new PlayerController(player, 200.0f));
    }

    protected void update(float et) {

    }

    protected void render(Graphics g) {

    }

    public static void main(String[] args) {
        new MainClass();
    }
}
