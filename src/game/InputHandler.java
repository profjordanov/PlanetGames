package game;

import display.Display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public InputHandler(Display display) {
        display.getCanvas().addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            Game.player.goingUp = true;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            Game.player.goingDown = true;
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            Game.player.goingLeft = true;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            Game.player.goingRight = true;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if(keyCode  == KeyEvent.VK_SPACE){
            Game.player.isShooting = true;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            Game.player.goingUp = false;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            Game.player.goingDown = false;
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            Game.player.goingLeft = false;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            Game.player.goingRight = false;
        }
        if(keyCode  == KeyEvent.VK_SPACE){
            Game.player.isShooting = false;

        }
    }
}
