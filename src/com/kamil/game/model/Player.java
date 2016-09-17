package com.kamil.game.model;

import com.kamil.game.main.Resources;

import java.awt.*;

@SuppressWarnings("WeakerAccess")
public class Player implements Renderable {

    private float x, y;
    private int width, height, yVelocity;

    private boolean isAlive;
    private boolean isDucked;

    private float duckDuration = .6f;
    private Rectangle runningBounds, duckBounds, ground;

    private static final int JUMP_VELOCITY = -600;
    private static final int ACCEL_GRAVITY = 1800;

    public Player(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        ground = new Rectangle(0, 405, 800, 45);
        runningBounds = new Rectangle();
        duckBounds = new Rectangle();

        isAlive = true;
        isDucked = false;
        updateRects();
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

    public int getyVelocity() {
        return yVelocity;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isDucked() {
        return isDucked;
    }

    public float getDuckDuration() {
        return duckDuration;
    }

    public Rectangle getRunningBounds() {
        return runningBounds;
    }

    public Rectangle getDuckBounds() {
        return duckBounds;
    }

    public Rectangle getGround() {
        return ground;
    }


    public void update(float delta) {
        if (duckDuration > 0 && isDucked) {
            duckDuration -= delta;
        } else {
            isDucked = false;
            duckDuration = .6f;
        }

        if (!isGrounded()) {
            yVelocity += ACCEL_GRAVITY * delta;
        } else {
            y = 406 - height;
            yVelocity = 0;
        }

        y += yVelocity * delta;
        updateRects();
    }

    private void updateRects() {
        runningBounds.setBounds((int) x + 10, (int) y, width - 20, height);
        duckBounds.setBounds((int) x, (int) y + 20, width, height - 20);
    }

    public void jump() {
        if (isGrounded()) {
            Resources.onJump.play();
            isDucked = false;
            duckDuration = .6f;
            y -= 10;
            yVelocity = JUMP_VELOCITY;
            updateRects();
        }
    }

    public void duck() {
        if (isGrounded()) isDucked = true;
    }

    public void pushBack(int dx) {
        Resources.hit.play();
        x -= dx;
        if (x < -width / 2) {
            isAlive = false;
        }
        runningBounds.setBounds(((int) x), (int) y, width, height);

    }

    public boolean isGrounded() {
        return runningBounds.intersects(ground);
    }

    @Override
    public void render(Graphics g) {
        int xInt = ((int) x);
        int yInt = ((int) y);

        if (isGrounded()) {
            if (isDucked) {
                g.drawImage(Resources.duck, xInt, yInt, null);
            } else {
                Resources.runAnimation.render(g, xInt, yInt, width, height);
            }
        } else {
            g.drawImage(Resources.jump, xInt, yInt, null);
        }

    }
}
