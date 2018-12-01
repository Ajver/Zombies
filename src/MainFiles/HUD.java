package MainFiles;

import jslEngine.jslSound;
import jslEngine.jslTimer;

import java.awt.*;

public class HUD {

    private static float hp = 100.0f;
    private static float maxHp = 100.0f;

    private static int magazines = 2;
    private static int magazineSize = 8;
    private static int ammo = magazineSize;
    private static jslTimer reloadTimer = new jslTimer(2.0f);

    private static float x, y, w, h;
    private static float padding = 2.0f;

    public static void update(float et) {
        if(reloadTimer.update()) {
            reloadTimer.stop();
            ammo = magazineSize;
            magazines--;
        }
    }

    public static void render(Graphics g) {
        g.setColor(new Color(0, 0,0));
        g.fillRect((int)(x-padding), (int)(y-padding), (int)(w+2.0f*padding), (int)(h+2.0f*padding));
        g.setColor(new Color(255, 0, 0));
        g.fillRect((int)x, (int)y, (int)(hp*w/maxHp), (int)h);

        g.setFont(new Font("arial", 0, 30));
        g.setColor(new Color(200, 200, 200));
        g.drawString(ammo + " / " + (magazines * magazineSize), (int)x, (int)y-40);
    }

    public static boolean getAmmo() {
        if(ammo > 0) {
            ammo--;

            reload();

            return true;
        }
        return false;
    }

    private static void reload() {
        if(ammo == 0) {
            // End of ammo!
            if (!reloadTimer.isRunning()) {
                if (magazines > 0) {
                    reloadTimer.restart();
                }
            }
        }
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

    public static void addMagazine(int nr) {
        magazines += nr;
        reload();
    }

    public static float getHp() { return hp;}
    public static float getMaxHp() { return maxHp;}

    public static void reset() {
        hp = maxHp;

        w = 300.0f;
        h = 25.0f;

        float margin = h * 2.0f;

        x = MainClass.WW - w - margin;
        y = MainClass.WH - h - margin;
    }


}
