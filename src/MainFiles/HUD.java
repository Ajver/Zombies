package MainFiles;

import java.awt.*;

public class HUD {

    private static float hp = 100.0f;
    private static float maxHp = 100.0f;

    private static float x, y, w, h;
    private static float padding = 2.0f;

    public static void update(float et) {
    }

    public static void render(Graphics g) {
        g.setColor(new Color(0, 0,0));
        g.fillRect((int)(x-padding), (int)(y-padding), (int)(w+2.0f*padding), (int)(h+2.0f*padding));
        g.setColor(new Color(255, 0, 0));
        g.fillRect((int)x, (int)y, (int)(hp*w/maxHp), (int)h);
    }

    public static void setHp(float hp) { HUD.hp = hp; }
    public static void setMaxHp(float maxHp) { HUD.maxHp = maxHp; }

    public static void addHp(float hp) {
        HUD.hp += hp;
        if(HUD.hp > HUD.maxHp) {
            HUD.hp = HUD.maxHp;
        }
        if(HUD.hp < 0) {
            HUD.hp = 0;
        }
    }

    public static float getHp() { return hp;}
    public static float getMaxHp() { return maxHp;}

    public static void reset() {
        hp = maxHp;

        w = 300.0f;
        h = 25.0f;

        x = (MainClass.WW - w) * 0.5f;
        y = MainClass.WH - h * 3.0f;
    }

}
