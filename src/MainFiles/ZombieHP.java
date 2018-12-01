package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslObject;

import java.awt.*;

public class ZombieHP extends jslObject {

    private float hp;
    private float maxHp;

    public ZombieHP(float maxHp, float zw, float w, float h) {
        setSize(w, h);
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.setLabel(jslLabel.ZOMBIE_HP);
        this.collisionBox.setHasBounds(false);
        this.translateY(-getH() * 1.5f);
        this.translateX((zw - getW()) * 0.5f);
    }

    public void update(float et) {

    }

    public void render(Graphics g) {
        g.setColor(new Color(0,0 ,0));
        float padding = 1.0f;
        g.drawRect((int)(getX()-padding), (int)(getY()-padding), (int)(getW() + padding*2.0f), (int)(getH() + padding*2.0f));

        g.setColor(new Color(255, 0, 0));
        g.fillRect((int)getX(), (int)getY(), (int)(hp*getW()/maxHp), (int)getH());
    }

    public boolean addHp(float hp) {
        this.hp += hp;

        if(this.hp <= 0) {
            this.hp = 0;
            return false;
        }
        if(this.hp > maxHp) {
            this.hp = maxHp;
        }

        return true;
    }
}
