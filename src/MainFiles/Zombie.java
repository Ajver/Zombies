package MainFiles;

import jslEngine.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class Zombie extends jslObject {

    private static int zombiesNr = 0;
    private static int maxZombies = 20;
    private static LinkedList<Zombie> zombies = new LinkedList<>();

    private BufferedImage texture = Texture.zombieImg;
    private Player player;
    private jslManager jsl;

    private CollisionBox collisionBox;
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
        collisionBox = new CollisionBox(this) {
            @Override
            public void onCollision(jslObject other) {
                if (other.is(jslLabel.BULLET)) {
                    jslVector2 v = new jslVector2(other.getVelX(), other.getVelY());
                    v.normalize();
                    v.multiply(16);
                    move(v.x, v.y);
                    jsl.removeObject(other);
                    if (!hp.addHp(-13 - r.nextInt(20))) {
                        zombiesNr--;
                        Stain stain = new Stain(o.getX(), o.getY(), o.getW(), o.getH());
                        stain.setRotate(o.getRotate());
                        jsl.add(stain);
                        zombies.add((Zombie)o);
                        jsl.removeObject(hp);
                        jsl.removeObject(o);
                    }
                } else {
                    switch (other.getLabel()) {
                        case PLAYER:
                            Camera.shake(500);
                        case WALL:
                        case ZOMBIE:
                            collisionBox.bound(other);
                            break;
                    }
                }
            }
        };

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
    }

    public void update(float et) {
        // Updating position
        {
            jslVector2 v = new jslVector2(player.getCenterX() - getCenterX(), player.getCenterY() - getCenterY());
            v.normalize();
            v.multiply(vel);
            move(v.x * et, v.y * et);

            float theta = (float) Math.atan2(v.x, v.y);
            setRotate(2 * (float) Math.PI - theta);
        }

        if(soundTimer.update()) {
            soundTimer.setDuration(r.nextInt(5) + 4);
            zombieSounds[r.nextInt(zombieSounds.length)].play();
        }

        // Collision
        collisionBox.collision(jsl);

        hp.setPosition(getX(), getY());

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

    public void render(Graphics g) {
        ((Graphics2D)g).rotate(getRotate(), getX() + getRotateX(), getY() + getRotateY());

        g.drawImage(texture, (int)getX(), (int)getY(), (int)getW(), (int)getH(),null);

        ((Graphics2D)g).rotate(-getRotate(), getX() + getRotateX(), getY() + getRotateY());
    }

    public static boolean newZombie(jslManager jsl, float x, float y) {
        if(zombies.isEmpty()) {
            return false;
        }

        // If all zombies, that should be spawned are spawned, do not create next
        if(zombies.size() + zombiesNr == maxZombies) {
            return false;
        }

        Zombie z = zombies.pop();
        z.reset(x, y);
        jsl.add(z);

        return true;
    }

    public static void fillZombies(float w, float h, jslManager jsl) {
        for(int i=0; i<maxZombies; i++) {
            zombies.add(new Zombie(w, h, jsl));
        }
    }

    public static int getZombiesNr() { return zombiesNr; }
    public static void setZombiesNr(int nr) { zombiesNr = nr; }
}
