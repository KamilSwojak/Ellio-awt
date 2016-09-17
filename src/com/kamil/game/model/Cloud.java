package com.kamil.game.model;

import com.kamil.framework.util.RandomNumberGenerator;
import com.kamil.game.main.Resources;

import java.awt.*;

public class Cloud implements Renderable {

    private float x, y;
    private int xVelocity;
    private int type;

    public static final int TYPE_01 = 1;
    public static final int TYPE_02 = 2;


    public Cloud(float x, float y, int type) {
        this.x = x;
        this.y = y;
        xVelocity = RandomNumberGenerator.getRandomBetween(-35, -10);
        this.type = type;
    }

    public void update(float delta) {
        x += xVelocity * delta;

        if (x <= -200) {
            //Reset to the right
            x += 1000;
            y = RandomNumberGenerator.getRandomBetween(20, 100);
            xVelocity = RandomNumberGenerator.getRandomBetween(-35, -10);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public void render(Graphics g) {
        if (type == TYPE_01) {
            g.drawImage(Resources.cloud1, (int) x, (int) y, null);
        } else if (type == TYPE_02) {
            g.drawImage(Resources.cloud2, (int) x, (int) y, null);
        }
    }
}
