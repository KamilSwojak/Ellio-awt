package com.kamil.game.state;

import com.kamil.game.main.Game;
import com.kamil.game.main.GameMain;
import com.kamil.game.main.Resources;
import com.kamil.game.model.Block;
import com.kamil.game.model.Cloud;
import com.kamil.game.model.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayState extends State {

    private Player player;
    private ArrayList<Block> blocks;
    private ArrayList<Cloud> clouds;

    private Font scoreFont;
    private int playerScore = 0;

    private static final int BLOCK_HEIGHT = 50;
    private static final int BLOCK_WIDTH = 20;
    private int blockSpeed = -200;

    public static final int PLAYER_WIDTH = 66;
    public static final int PLAYER_HEIGHT = 92;

    private static final String pauseString = "paused";
    private Font pauseFont;

    private boolean isPaused = false;

    @Override
    public void init() {
        player = new Player(160, GameMain.GAME_HEIGHT - 45 - PLAYER_WIDTH, PLAYER_WIDTH, PLAYER_HEIGHT);

        clouds = new ArrayList<>();
        Cloud cloud1 = new Cloud(100, 100, 1);
        Cloud cloud2 = new Cloud(500, 50, 2);
        Cloud cloud3 = new Cloud(750, 50, 2);
        Cloud cloud4 = new Cloud(300, 50, 1);

        clouds.add(cloud1);
        clouds.add(cloud2);
        clouds.add(cloud3);
        clouds.add(cloud4);

        scoreFont = new Font("SansSerif", Font.BOLD, 25);
        pauseFont = new Font("SansSerif", Font.BOLD, 40);

        blocks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Block b = new Block(i * 200, GameMain.GAME_HEIGHT - 95, BLOCK_WIDTH, BLOCK_HEIGHT);
            blocks.add(b);
        }
    }

    @Override
    public void update(float deltaMillis) {
        if (isPaused) return;

        if (!player.isAlive()) {
            setCurrentState(new GameOverState(playerScore));
        }
        playerScore += 1;

        if (playerScore % 500 == 0 && blockSpeed > -280) {
            blockSpeed -= 10;
        }

        Resources.runAnimation.update(deltaMillis);
        player.update(deltaMillis);

        for (Cloud cloud : clouds) {
            cloud.update(deltaMillis);
        }
        for (Block block : blocks) {
            block.update(deltaMillis, blockSpeed);

            if (block.isVisible()) {
                if (player.isDucked() && player.getDuckBounds().intersects(block.getBounds())) {
                    block.onCollide(player);
                } else if (!player.isDucked() && player.getRunningBounds().intersects(block.getBounds())) {
                    block.onCollide(player);
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Resources.skyBlue);
        g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);

        player.render(g);

        for (Block block : blocks) {
            if (block.isVisible()) block.render(g);
        }

        for (Cloud cloud : clouds) {
            cloud.render(g);
        }
        g.drawImage(Resources.grass, 0, 405, null);
        renderSun(g);

        g.setColor(Color.GRAY);
        g.setFont(scoreFont);
        g.drawString(playerScore / 100 + "", 20, 30);


        FontMetrics fontMetrics = g.getFontMetrics(pauseFont);
        int stringWidth = fontMetrics.stringWidth(pauseString);
        if (isPaused) {
            g.setColor(Color.green);
            g.setFont(pauseFont);
            g.drawString(pauseString, GameMain.GAME_WIDTH / 2 - stringWidth / 2, GameMain.GAME_HEIGHT / 2);
        }
    }

    private void renderSun(Graphics g) {
        g.setColor(Color.orange);
        g.fillOval(715, -85, 170, 170);
        g.setColor(Color.YELLOW);
        g.fillOval(725, -75, 150, 150);
    }

    @Override
    public void onClick(MouseEvent e) {

    }

    @Override
    public void onKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
                player.jump();
                break;
            case KeyEvent.VK_DOWN:
                player.duck();
                break;
            case KeyEvent.VK_P:
                togglePause();
                break;
            case KeyEvent.VK_ESCAPE:
                if (isPaused) {
                    setCurrentState(new MenuState());
                } else {
                    togglePause();
                }
                break;

        }
    }

    @Override
    public void onKeyRelease(KeyEvent e) {

    }

    private void togglePause() {
        isPaused ^= true;
    }

}
