package MainFiles;

import jslEngine.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Zombie extends jslObject {

    private BufferedImage texture = Texture.zombieImg;
    private Player player;
    private jslManager jsl;

    private ZombieHP hp;

    private float vel;

    private float strange;
    private float hitRate = 1.0f;
    private float timer = 1.0f / hitRate;
    private boolean isReady = false;

    private jslTimer soundTimer;
    private jslSound[] zombieSounds = {
            new jslSound("res/sounds/zombie1.wav"),
            new jslSound("res/sounds/zombie2.wav"),
            new jslSound("res/sounds/zombie3.wav"),
            new jslSound("res/sounds/zombie4.wav")
    };

    public Zombie(float x, float y, float w, float h, float vel, Player player, jslManager jsl) {
        super(x, y, w, h);
        this.vel = vel;
        this.player = player;
        this.setRotateToCenter();
        this.setLabel(jslLabel.ZOMBIE);
        this.strange = 10.0f;
        this.jsl = jsl;

        jsl.add(this.hp = new ZombieHP(50, getW(),getW()*1.5f, 15));

        Random r = new Random();
        soundTimer = new jslTimer((r.nextInt(1000) + 3000) / 1000.0f);
        soundTimer.start();

        for(int i=0; i<zombieSounds.length; i++) {
            zombieSounds[i].setLevel(0.6f);
        }
    }

    public void update(float et) {
        jslVector2 v = new jslVector2(player.getCenterX() - getCenterX(), player.getCenterY() - getCenterY());
        v.normalize();
        v.multiply(vel);
        setVel(v);

        float theta = (float)Math.atan2(v.x, v.y);
        setRotate(2*(float)Math.PI - theta);

        if(soundTimer.update(et)) {
            Random r = new Random();
            soundTimer.setDuration((r.nextInt(5000) + 4000) / 1000.0f);
            zombieSounds[r.nextInt(zombieSounds.length)].play();
        }

        hp.setPosition(getX(), getY());
    }

    public void afterUpdate(float et) {
        if(!isReady) {
            timer -= et;

            if(timer <= 0) {
                timer = 1.0f / hitRate;
                isReady = true;
            }
        }else {
            float dx = getCenterX() - player.getCenterX();
            float dy = getCenterY() - player.getCenterY();

            if (Math.abs(dx) <= (player.getW() + getW()) * 0.6f) {
                if (Math.abs(dy) <= (player.getH() + getH()) * 0.5f) {
                    HUD.addHp(-strange);
                    isReady = false;
                }
            }
        }
    }

    public void onCollision(jslObject other) {
        if(other.is(jslLabel.BULLET)) {
            jslVector2 v = new jslVector2(other.getVelX(), other.getVelY());
            v.normalize();
            v.multiply(16);
            move(v.x, v.y);
            jsl.removeObject(other);
            if(!hp.addHp(-20)) {
                jsl.removeObject(hp);
                jsl.removeObject(this);
            }
        }else {
            collisionBox.bound(other);
        }
    }

    public void render(Graphics g) {
        g.drawImage(texture, (int)getX(), (int)getY(), (int)getW(), (int)getH(), null);
    }
}
