package MainFiles;

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

        // Leave animation
        LEAVE,

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

    // Wait until the leave animation end
    private jslTimer leaveTimer;

    private int level = 1;

    public LevelManager(float start, float end, jslManager jsl) {
        this.jsl = jsl;

        beginTimer = new jslTimer(start);
        pauseTimer = new jslTimer(5.0f);
        leaveTimer = new jslTimer(end);

        beginTimer.start();
    }

    public State update() {
        /*
           If state has been changed, it returns new value. Else it returns DEFAULT state.
         */
        switch (state) {
            case ENTER:
                if(beginTimer.update()) {
                    state = State.GAME;
                    return State.GAME;
                }
                break;
            case GAME:
                if(Zombie.getZombiesNr() <= 0) {
                    state = State.PAUSE;
                    pauseTimer.restart();
                    return State.PAUSE;
                }
                break;
            case PAUSE:
                if(pauseTimer.update()) {
                    state = State.GAME;
                    Zombie.fillZombies(MainClass.creatureSize, MainClass.creatureSize, jsl, 5);
                    return State.GAME;
                }
                break;
            case LEAVE:
                if(leaveTimer.update()) {
                    state = State.END;
                    return State.END;
                }
                break;
        }

        return State.DEFAULT;
    }

    public int getLevel() { return level; }
    public State getState() { return state; }

}
