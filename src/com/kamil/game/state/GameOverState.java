package com.kamil.game.state;

import com.kamil.game.main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameOverState extends State {

    private String score;
    private Font scoreFont;

    public GameOverState(int score) {
        System.out.println("GameOverState.GameOverState");
        this.score = score + "";
        scoreFont = new Font("SansSerif", Font.BOLD, 50);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float deltaMillis) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.darkGray);
        g.setFont(scoreFont);
        g.drawString("GAME OVER", 257, 175);
        g.drawString(score, 385, 250);
        g.drawString("Press any key", 240, 350);
    }

    @Override
    public void onClick(MouseEvent e) {

    }

    @Override
    public void onKeyPress(KeyEvent e) {
        setCurrentState(new MenuState());
    }

    @Override
    public void onKeyRelease(KeyEvent e) {

    }
}
