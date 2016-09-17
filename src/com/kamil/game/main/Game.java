package com.kamil.game.main;


import com.kamil.game.state.LoadState;
import com.kamil.game.state.State;
import com.kamil.framework.util.InputHandler;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {

    private volatile State currentState;

    private Image gameImage;
    private int gameWidth;
    private int gameHeight;

    private Thread gameThread;
    private volatile boolean running = false;

    private InputHandler inputHandler;

    Game(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        setPreferredSize(new Dimension(gameWidth, gameHeight));
        setBackground(Color.black);
        setFocusable(true);
        requestFocus();
    }

    public void setCurrentState(State newState) {
        System.gc();
        newState.init();
        currentState = newState;
        inputHandler.setCurrentState(currentState);
    }

    @Override
    public void addNotify() {
        System.out.println("Game.addNotify");
        super.addNotify();
        initInput();
        setCurrentState(new LoadState());
        initGame();
    }

    private void initGame() {
        running = true;
        gameThread = new Thread(this, this.getClass().getSimpleName());
        gameThread.start();
    }

    private void initInput() {
        inputHandler = new com.kamil.framework.util.InputHandler();
        addKeyListener(inputHandler);
        addMouseListener(inputHandler);

    }

    @Override
    public void run() {
        System.out.println("Game.run");

        long updateDurationMillis = 0L;
        long sleepDurationMillis = 0L;
        while (running) {
            long beforeUpdateAndRender = System.nanoTime();
            long deltaMillis = updateDurationMillis + sleepDurationMillis;

            updateAndRender(deltaMillis);

            updateDurationMillis = (System.nanoTime() - beforeUpdateAndRender) / 1000000;
            sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);

            try {
                Thread.sleep(sleepDurationMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.exit(0);
    }

    private void updateAndRender(long deltaMillis) {
        currentState.update(deltaMillis / 1000f);
        prepareGameImage();
        currentState.render(gameImage.getGraphics());
        renderGameImage(getGraphics());
    }

    private void prepareGameImage() {
        if (gameImage == null) {
            gameImage = createImage(gameWidth, gameHeight);
        }
        gameImage.getGraphics().clearRect(0, 0, gameWidth, gameHeight);
    }

    private void renderGameImage(Graphics g) {
        if (gameImage != null) {
            g.drawImage(gameImage, 0, 0, null);
        }
        g.dispose();
    }

    public void exit() {
        running = false;
    }
}
