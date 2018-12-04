package MainFiles;

import jslEngine.jslManager;
import jslEngine.jslSound;
import jslEngine.jslTimer;
import jslEngine.jslVector2;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Shotgun {

    private jslManager jsl;
    private Player player;
    private int mx = 0, my = 0;

    private float frameRate = 4.0f;
    private jslTimer shotTimer;
    private boolean ready = false;

    private boolean isShoting = false;
    private jslSound shotSound;

    public Shotgun(Player player, jslManager jsl) {
        this.player = player;
        this.jsl = jsl;
        this.shotSound = new jslSound("res/sounds/shot.wav");
        this.shotSound.setLevel(0.55f);
        this.shotTimer = new jslTimer(1.0f / frameRate);
        this.shotTimer.start();
    }

    public void update(float et) {
        if(shotTimer.update()) {
            ready = true;
            shotTimer.stop();
        }

        if(isShoting) {
            shot();
        }
    }

    private void shot() {
        if(ready) {
            if(HUD.getAmmo()) {
                shotTimer.restart();
                shotSound.play();
                ready = false;

                jslVector2 v = new jslVector2(this.mx - MainClass.WW * 0.5f, this.my - MainClass.WH * 0.5f);
                v.normalize();
                v.multiply(1200.0f);

                jslVector2 v2 = new jslVector2(0, player.getH() * 0.5f);
                v2.rotate(player.getRotate());

                Bullet bullet = new Bullet(player.getCenterX() + v2.x, player.getCenterY() + v2.y, 8, 8, jsl);
                bullet.setVel(v.x, v.y);

                jsl.add(bullet);
            }
        }
    }

    public void onPress(MouseEvent e) {
        this.mx = e.getX();
        this.my = e.getY();

        isShoting = true;

        shot();
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
        player.setRotate(2*(float)Math.PI - theta);
    }
}
