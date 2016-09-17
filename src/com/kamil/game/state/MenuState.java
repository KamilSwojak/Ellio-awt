package com.kamil.game.state;

import com.kamil.game.main.Game;
import com.kamil.game.main.GameMain;
import com.kamil.game.main.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuState extends State {

    private int currentSelection = 0;

    @Override
    public void init() {
        System.out.println("MenuState.init");
    }

    @Override
    public void update(float deltaMillis) {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Resources.welcome, 0, 0, null);

        if (currentSelection == 0) {
            g.drawImage(Resources.selector, 335, 241, null);
        } else {
            g.drawImage(Resources.selector, 335, 291, null);
        }
    }

    @Override
    public void onClick(MouseEvent e) {

    }

    @Override
    public void onKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                if (currentSelection == 0) {
                    setCurrentState(new PlayState());
                } else {
                    GameMain.sGame.exit();
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_UP:
                if (currentSelection == 0) {
                    currentSelection = 1;
                } else {
                    currentSelection = 0;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                GameMain.sGame.exit();
                break;
        }

    }

    @Override
    public void onKeyRelease(KeyEvent e) {
    }
}
