package MainFiles;

import jslEngine.*;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class MainClass extends jslEngine {

    private Player player;

    private MainClass() {
        start("Zombies", 1000, 600);

        jslVector2 v1, v2;

        v2 = new jslVector2(3, 5);

        v1 = new jslVector2(-1, 2);
        System.out.println(v2.getX() + " | " + v2.getY());

        v1.normalize();
        System.out.println(v1.getX() + " | " + v1.getY());
    }

    protected void onCreate() {
        player = new Player(WW() * 0.5f, WH() * 0.5f, 32, 32);
        jsl.add(player);
        jsl.add(new PlayerController(player, 350.0f));
    }

    protected void update(float et) {

    }

    protected void render(Graphics g) {

    }

    public static void main(String[] args) {
        new MainClass();
    }
}
