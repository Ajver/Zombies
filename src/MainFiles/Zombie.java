package MainFiles;

import jslEngine.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class Zombie extends jslObject {

    private static LinkedList<Zombie> zombies = new LinkedList<>();

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
    private jslSound[] zombieSounds = new jslSound[4];
    private Random r = new Random();

    public Zombie(float w, float h, jslManager jsl) {
        setSize(w, h);

        this.player = (Player)jsl.getObject(jslLabel.PLAYER);
        this.setRotateToCenter();
        this.setLabel(jslLabel.ZOMBIE);
        this.jsl = jsl;
        this.strange = 10.0f;
        this.vel = 100.0f;

        soundTimer = new jslTimer((r.nextInt(4) + 3));

        for(int i=0; i<zombieSounds.length; i++) {
            zombieSounds[i] = new jslSound("res/sounds/zombie" + (i+1) + ".wav");
            zombieSounds[i].setLevel(0.6f);
        }

        reset(0, 0);
    }

    public void reset(float x, float y) {
        setPosition(x, y);
        jsl.add(this.hp = new ZombieHP(50, getW(),getW()*1.5f, 10));
        soundTimer.restart();
//        for(int i=0; i<zombieSounds.length; i++) {
//            zombieSounds[i].load();
//        }
    }

    public void update(float et) {
        jslVector2 v = new jslVector2(player.getCenterX() - getCenterX(), player.getCenterY() - getCenterY());
        v.normalize();
        v.multiply(vel);
        setVel(v.x, v.y);

        float theta = (float)Math.atan2(v.x, v.y);
        setRotate(2*(float)Math.PI - theta);

        if(soundTimer.update()) {
            soundTimer.setDuration(r.nextInt(5) + 4);
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
            if(!hp.addHp(-13 - r.nextInt(20))) {
                zombies.add(this);
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

    public static void newZombie(jslManager jsl, float x, float y) {
        if(zombies.isEmpty()) {
            return;
        }

        Zombie z = zombies.pop();
        z.reset(x, y);
        jsl.add(z);
    }

    public static void fillZombies(float w, float h, jslManager jsl) {
        for(int i=0; i<20; i++) {
            zombies.add(new Zombie(w, h, jsl));
        }
    }
}
