package jslEngine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class jslSound {

    private String path = null;
    private Clip clip = null;
    private float level = 1.0f; // < 0; 1 >

    public jslSound() { }

    public jslSound(String path) {
        load(path);
    }

    // Continue playing
    public boolean play() {
        if(clip != null) {
            if(clip.isOpen()) {
                clip.start();
                return true;
            }else {
                return play(path);
            }
        }
        return false;
    }

    // Load sound and play it
    public boolean play(String path) {
        load(path);
        return play();
    }

    // Start from begin
    public boolean replay() {
        return play(path);
    }

    // Stop but not end the sound
    public void pause() {
        clip.stop();
    }

    // End the sound (next time will start from begin)
    public void stop() {
        clip.close();
    }

    // Load the sound (will not play it)
    public boolean load(String path) {
        this.path = path;

        File f = new File(path);
        AudioInputStream audioIn;
        try {
            audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(limit(control,level));

            return true;
        } catch(Exception e) {
            System.out.println("Path: " + path);
            e.printStackTrace();
            return false;
        }
    }

    public void setLevel(float level) {
        this.level = level;
        FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(limit(control,level));
    }

    public float getLevel() { return level; }

    private float limit(FloatControl control, float level) {
        level *= 86;
        level -= 70;
        return Math.min(control.getMaximum(), Math.max(control.getMinimum(), level));
    }

    public boolean isRunning() { return clip.isRunning(); }
}
