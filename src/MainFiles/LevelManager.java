package MainFiles;

import Objects.SpitingZombie;
import Objects.Zombie;
import Objects.ZombieSpawner;
import jslEngine.jslManager;
import jslEngine.jslTimer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class LevelManager {

    public enum State {

        // Normal game (player is killing the zombies)
        GAME,

        // After all zombies in level were killed
        PAUSE,

        // End of map - leave the game
        END,

        // If state is the same like in previous frame
        // (Needed for update() method
        DEFAULT
    }

    // State of the game
    private State state = State.GAME;

    private jslManager jsl;

    private ArrayList<ZombieSpawner> zombieSpawners;

    private static LinkedList<Zombie> zombies = new LinkedList<>();

    // Break between levels
    private jslTimer pauseTimer;

    private int level = 0;

    public LevelManager(ArrayList<ZombieSpawner> zombieSpawners, jslManager jsl) {
        this.zombieSpawners = zombieSpawners;
        this.jsl = jsl;

        for(int i=0; i<20; i++) {
            zombies.add(new Zombie(MainClass.creatureSize, MainClass.creatureSize, jsl));
        }
        for(int i=0; i<10; i++) {
            zombies.add(new SpitingZombie(MainClass.creatureSize, MainClass.creatureSize, jsl));
        }

        pauseTimer = new jslTimer(5.0f);
    }

    public State update() {
        /*
           If state has been changed, it returns new value. Else it returns DEFAULT state.
         */
        switch (state) {
            case GAME:
                if(Zombie.getZombiesNr() <= 0) {
                    if(level < 5) {
                        pauseTimer.restart();
                        return state = State.PAUSE;
                    }else {
                        return state = State.END;
                    }
                }else {

                }
                break;
            case PAUSE:
                if(pauseTimer.update()) {
                    nextLevel();
                    return state = State.GAME;
                }
                break;
        }

        return State.DEFAULT;
    }

    private void nextLevel() {
        level++;
        Zombie.setZombiesNr(4 + level * 3);
    }

    public static boolean newZombie(float x, float y, jslManager jsl) {
        // All zombies are spawned
        if(zombies.isEmpty()) {
            return false;
        }

        // If all zombies, that should be spawned (in this level) are spawned, do not create next
//        if(zombies.size() + zombiesNr == maxZombies) {
//            return false;
//        }

        Zombie z = zombies.remove((new Random()).nextInt(zombies.size()));
        z.reset(x, y);
        jsl.add(z);

        return true;
    }

    public static void addZombie(Zombie z) {
        zombies.add(z);
    }

    public int getLevel() { return level; }
    public State getState() { return state; }

}
