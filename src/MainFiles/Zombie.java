package MainFiles;

import jslEngine.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class Zombie extends jslObject {

    // How many zombies are in this level
    private static int zombiesNr = 0;

    // Max zombies on map in the same place
    private static int maxZombies = 20;

    // Waiting area
    // Zombies to spawn (created once, then adding into jslManager)
    private static LinkedList<Zombie> zombies = new LinkedList<>();

    private BufferedImage texture = Texture.zombieImg;
    private Player player;
    private jslManager jsl;

    private CollisionBox collisionBox;
    private ZombieHP hp;

    // Velocity of moving
    private float vel = 100.0f;

    private float strange = 10.0f;
    private float hitRate = 1.0f;
    private jslTimer hitTimer;
    private boolean isReady;

    private jslTimer soundTimer;
    private jslSound[] zombieSounds = new jslSound[4];
    private Random r = new Random();

    public Zombie(float w, float h, jslManager jsl) {
        setSize(w, h);

        this.player = (Player)jsl.getObject(jslLabel.PLAYER);
        this.setRotateToCenter();
        this.setLabel(jslLabel.ZOMBIE);
        this.jsl = jsl;

        soundTimer = new jslTimer((r.nextInt(4) + 3));
        collisionBox = new CollisionBox(this) {
            @Override
            public void onCollision(jslObject other) {
                if (other.is(jslLabel.BULLET)) {
                    // Vector to be punched
                    jslVector2 v = new jslVector2(other.getVelX(), other.getVelY());
                    v.normalize();
                    v.multiply(16);
                    move(v.x, v.y);

                    // Remove bullet
                    jsl.removeObject(other);
                    if (!hp.addHp(-13 - r.nextInt(20))) {
                        zombiesNr--;

                        Stain stain = new Stain(o.getX(), o.getY(), o.getW(), o.getH());
                        stain.setRotate(o.getRotate());

                        // Back zombie to waiting area
                        zombies.add((Zombie)o);
                        jsl.add(stain);
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

        hitTimer = new jslTimer(1.0f / hitRate);

        reset(0, 0);
    }

    public void reset(float x, float y) {
        isReady = false;
        hitTimer.restart();
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

            // Rotate to player
            float theta = (float) Math.atan2(v.x, v.y);
            setRotate(2 * (float) Math.PI - theta);
        }

        if(soundTimer.update()) {
            soundTimer.setDuration(r.nextInt(5) + 4);
            zombieSounds[r.nextInt(zombieSounds.length)].play();
        }

        collisionBox.collision(jsl);

        hp.setPosition(getX(), getY());

        if(!isReady) {
            if(hitTimer.update()) {
                isReady = true;
            }
        }else {
            // If player is close enough...
            float dx = getCenterX() - player.getCenterX();
            float dy = getCenterY() - player.getCenterY();

            if (Math.abs(dx) <= (player.getW() + getW()) * 0.6f) {
                if (Math.abs(dy) <= (player.getH() + getH()) * 0.5f) {
                    // ...hit him
                    HUD.addHp(-strange);

                    // Restart hit timer
                    hitTimer.restart();
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
        // All zombies are spawned
        if(zombies.isEmpty()) {
            return false;
        }

        // If all zombies, that should be spawned (in this level) are spawned, do not create next
        if(zombies.size() + zombiesNr == maxZombies) {
            return false;
        }

        Zombie z = zombies.pop();
        z.reset(x, y);
        jsl.add(z);

        return true;
    }

    public static void fillZombies(float w, float h, jslManager jsl) {
        // Fill waiting area
        for(int i=0; i<maxZombies; i++) {
            zombies.add(new Zombie(w, h, jsl));
        }
    }

    public static int getZombiesNr() { return zombiesNr; }
    public static void setZombiesNr(int nr) { zombiesNr = nr; }
}
