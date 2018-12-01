package MainFiles;

import jslEngine.jslLabel;
import jslEngine.jslManager;
import jslEngine.jslObject;

public class Item extends jslObject {

    private jslManager jsl;
    private ItemSpawner spawner;

    public Item(float x, float y, float w, float h, jslManager jsl, ItemSpawner spawner) {
        super(x, y, w, h);
        this.jsl = jsl;
        this.spawner = spawner;
        this.setLabel(jslLabel.ITEM);
        this.collisionBox.setHasBounds(false);
    }

    public void onCollision(jslObject other) {
        if(other.is(jslLabel.PLAYER)) {
            spawner.clear();
            onPick();
            jsl.removeObject(this);
        }
    }

    protected void onPick() {}
}
