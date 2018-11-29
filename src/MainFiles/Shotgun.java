package MainFiles;

import jslEngine.jslManager;
import jslEngine.jslVector2;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Shotgun {

    private jslManager jsl;
    private Player player;
    private int mx = 0, my = 0;

    private float frameRate = 4.0f;
    private float timer = 1.0f / frameRate;
    private boolean ready = false;

    private boolean isShoting = false;

    public Shotgun(Player player, jslManager jsl) {
        this.player = player;
        this.jsl = jsl;
    }

    public void update(float et) {
        if(!ready) {
            timer -= et;

            if (timer <= 0) {
                timer = 1.0f / frameRate;
                ready = true;
            }
        }

        if(isShoting) {
            shoot();
        }
    }

    public void render(Graphics g) {
        // Draw crosshair

    }

    private void shoot() {
        if(ready) {
            ready = false;

            jslVector2 v = new jslVector2(this.mx - MainClass.WW * 0.5f, this.my - MainClass.WH * 0.5f);
            v.normalize();
            v.multiply(1200.0f);

            Bullet bullet = new Bullet(player.getCenterX(), player.getCenterY(), 8,8);
            bullet.setVel(v.x, v.y);

            jsl.add(bullet);
        }
    }

    public void onPress(MouseEvent e) {
        this.mx = e.getX();
        this.my = e.getY();

        isShoting = true;

        shoot();
    }

    public void onRelease(MouseEvent e) {
        isShoting = false;
    }

    public void onMove(MouseEvent e) {
        this.mx = e.getX();
        this.my = e.getY();

        float dx = this.mx - MainClass.WW * 0.5f;
        float dy = this.my - MainClass.WH * 0.5f;
        float theta = (float)Math.atan2(dx, dy);
        player.setTheta(2*(float)Math.PI - theta);
    }
}