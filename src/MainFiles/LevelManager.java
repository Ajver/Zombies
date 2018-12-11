package MainFiles;

import Objects.Zombie;
import jslEngine.jslManager;
import jslEngine.jslTimer;

public class LevelManager {

    public enum State {
        // Start animation
        ENTER,

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
    private State state = State.ENTER;

    private jslManager jsl;

    // Wait until the start animation end
    private jslTimer beginTimer;

    // Break between levels
    private jslTimer pauseTimer;

    private int level = 0;

    public LevelManager(float start, jslManager jsl) {
        this.jsl = jsl;

        beginTimer = new jslTimer(start);
        pauseTimer = new jslTimer(5.0f);

        beginTimer.start();
    }

    public State update() {
        /*
           If state has been changed, it returns new value. Else it returns DEFAULT state.
         */
        switch (state) {
            case ENTER:
                if(beginTimer.update()) {
                    nextLevel();
                    return state = State.GAME;
                }
                break;
            case GAME:
                if(Zombie.getZombiesNr() <= 0) {
                    if(level < 5) {
                        pauseTimer.restart();
                        return state = State.PAUSE;
                    }else {
                        return state = State.END;
                    }
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

    public int getLevel() { return level; }
    public State getState() { return state; }

}
