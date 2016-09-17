package com.kamil.framework.animation;

import java.awt.*;

public class Frame {

    private Image image;
    private double duration;

    public Frame(Image image, double duration) {
        this.image = image;
        this.duration = duration;
    }

    public Image getImage() {
        return image;
    }

    public double getDuration() {
        return duration;
    }
}
