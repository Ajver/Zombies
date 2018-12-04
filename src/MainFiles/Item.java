package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;
import jslEngine.jslSound;

import java.awt.image.BufferedImage;

public class Item extends jslObject {

    private jslManager jsl;
    private ItemSpawner spawner;
    private CollisionBox collisionBox;

    protected BufferedImage image;
    protected jslSound pickupSound;

    public Item(float x, float y, float w, float h, jslManager jsl, ItemSpawner spawner) {
        super(x, y, w, h);
        this.jsl = jsl;
        this.spawner = spawner;
        this.setLabel(jslLabel.ITEM);
        this.collisionBox = new CollisionBox(this) {
            @Override
            public void onCollision(jslObject other) {
                if(other.is(jslLabel.PLAYER)) {
                    spawner.clear();
                    onPick();
                    jsl.removeObject(o);
                }
            }
        };
    }

    public void update(float et) {
        collisionBox.collision(jsl);
    }

    protected void onPick() {}
}
