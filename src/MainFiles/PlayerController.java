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
                if(isLeft || isRight) {
                    player.setVelX(player.getVelX() / sqrt2);
                    player.setVelY(-vel / sqrt2);
                }else {
                    player.setVelY(-vel);
                }
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                isDown = true;
                if(isLeft || isRight) {
                    player.setVelX(player.getVelX() / sqrt2);
                    player.setVelY(vel / sqrt2);
                }else {
                    player.setVelY(vel);
                }
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                isLeft = true;
                if(isUp || isDown) {
                    player.setVelY(player.getVelY() / sqrt2);
                    player.setVelX(-vel / sqrt2);
                }else {
                    player.setVelX(-vel);
                }
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                isRight = true;
                if(isUp || isDown) {
                    player.setVelY(player.getVelY() / sqrt2);
                    player.setVelX(vel / sqrt2);
                }else {
                    player.setVelX(vel);
                }
                break;
        }
    }

    public void onRelease(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                isUp = false;
                if(isDown) {
                    if(isLeft || isRight) {
                        player.setVelX(player.getVelX() / sqrt2);
                        player.setVelY(vel / sqrt2);
                    }else {
                        player.setVelY(vel);
                    }
                }else {
                    player.setVelY(0);
                    if(isLeft != isRight) {
                        player.setVelX(vel * (isLeft ? -1 : 1));
                    }
                }
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                isDown = false;
                if(isUp) {
                    if(isLeft || isRight) {
                        player.setVelX(player.getVelX() / sqrt2);
                        player.setVelY(-vel / sqrt2);
                    }else {
                        player.setVelY(-vel);
                    }
                }else {
                    player.setVelY(0);
                    if(isLeft != isRight) {
                        player.setVelX(vel * (isLeft ? -1 : 1));
                    }
                }
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                isLeft = false;
                if(isRight) {
                    if(isUp || isDown) {
                        player.setVelY(player.getVelY() / sqrt2);
                        player.setVelX(vel / sqrt2);
                    }else {
                        player.setVelX(vel);
                    }
                }else {
                    player.setVelX(0);
                    if(isUp != isDown) {
                        player.setVelY(vel * (isUp ? -1 : 1));
                    }
                }
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                isRight = false;
                if(isLeft) {
                    if(isUp || isDown) {
                        player.setVelY(player.getVelY() / sqrt2);
                        player.setVelX(-vel / sqrt2);
                    }else {
                        player.setVelX(-vel);
                    }
                }else {
                    player.setVelX(0);
                    if(isUp != isDown) {
                        player.setVelY(vel * (isUp ? -1 : 1));
                    }
                }
                break;
        }
    }
}
