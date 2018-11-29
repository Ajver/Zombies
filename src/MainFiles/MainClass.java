package MainFiles;

import jslEngine.*;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class MainClass extends jslEngine {

    private MainClass() {
        start("Zombies", 600, 400);

    }

    protected void update(float et) {


    }

    protected void render(Graphics g) {

    }

    public static void main(String[] args) {
        new MainClass();
    }
}
