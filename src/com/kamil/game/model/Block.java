package com.kamil.game.model;

import com.kamil.framework.util.RandomNumberGenerator;
import com.kamil.game.main.Resources;

import java.awt.*;

public class Block implements Renderable {

    private float x, y;
    private int width, height;

    private Rectangle bounds;

    private boolean isVisible;

    public static final int UPPER_Y = 275;
    public static final int LOWER_Y = 355;


    public Block(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle();
        isVisible = false;
    }

    public void update(float delta, float velX) {
        x += velX * delta;
        if (x <= -50) {
            reset();
        }
        updateRects();
    }

    private void updateRects() {
        bounds.setBounds((int) x, (int) y, width, height);
    }

    private void reset() {
        isVisible = true;

        if (RandomNumberGenerator.getRandom(3) == 0) {
            y = UPPER_Y;
        } else {
            y = LOWER_Y;
        }

        x += 1000;
    }

    public void onCollide(Player p) {
        isVisible = false;
        p.pushBack(30);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Resources.block, ((int) x), ((int) y), null);
    }
}
