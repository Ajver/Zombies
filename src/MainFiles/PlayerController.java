package MainFiles;

import jslEngine.jslKeyInput;

import java.awt.event.KeyEvent;

public class PlayerController extends jslKeyInput {

    private Player player;
    private boolean isUp = false, isDown = false, isLeft = false, isRight = false;
    private float vel;
    private final float sqrt2 = 1.41421f;

    public PlayerController(Player player, float vel) {
        this.player = player;
        this.vel = vel;
    }

    public void onPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                isUp = true;
                player.setVelY(-vel);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                isDown = true;
                player.setVelY(vel);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                isLeft = true;
                player.setVelX(-vel);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                isRight = true;
                player.setVelX(vel);
                break;
        }
    }

    public void onRelease(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                isUp = false;
                if(isDown) {
                    player.setVelY(vel);
                }else {
                    player.setVelY(0);
                }
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                isDown = false;
                if(isUp) {
                    player.setVelY(-vel);
                }else {
                    player.setVelY(0);
                }
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                isLeft = false;
                if(isRight) {
                    player.setVelX(vel);
                }else {
                    player.setVelX(0);
                }
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                isRight = false;
                if(isLeft) {
                    player.setVelX(-vel);
                }else {
                    player.setVelX(0);
                }
                break;
        }
    }
}
