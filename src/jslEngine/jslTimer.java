package jslEngine;

public class jslTimer {

    // Animation duration in millis
    private float duration;
    private float timer;

    private boolean isRunning = false;

    public jslTimer(float duration) {
        setDuration(duration);
        reset();
    }

    public boolean update(float et) {
        if(!isRunning) {
            return false;
        }

        if((timer -= et) <= 0) {
            reset();
            return true;
        }

        return false;
    }

    public void restart() {
        reset();
        start();
    }

    public void reset() {
        timer = duration;
    }

    public void stop() {
        isRunning = false;
    }

    public void start() {
        isRunning = true;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public boolean isRunning() { return isRunning; }
    public float getDuration() { return duration; }
    public float getTimer() { return timer; }
}
