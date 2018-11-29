package MainFiles;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Shotgun {

    private Player player;
    private int mx = 0, my = 0;

    public Shotgun(Player player) {
        this.player = player;
    }

    public void update(float et) {

    }

    public void render(Graphics g) {
        // Draw crosshair

    }

    public void onMove(MouseEvent e) {
        this.mx = e.getX();
        this.my = e.getY();

        float dx = this.mx - MainClass.WW * 0.5f;
        float dy = this.my - MainClass.WH * 0.5f;
        float theta = (float)Math.atan2(dx, dy);
        player.setTheta((float)Math.PI - theta);
    }
}
