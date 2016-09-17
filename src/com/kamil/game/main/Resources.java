package com.kamil.game.main;

import com.kamil.framework.animation.Animation;
import com.kamil.framework.animation.Frame;

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@SuppressWarnings("WeakerAccess")
public class Resources {

    public static BufferedImage welcome, iconImage, block, cloud1, cloud2,
            duck, grass, jump, run1, run2, run3, run4, run5, selector;

    public static AudioClip hit, onJump;

    public static Animation runAnimation;
    public static Color skyBlue;

    public static void load() {
        welcome = loadImage("welcome.png");
        iconImage = loadImage("iconimage.png");
        block = loadImage("block.png");
        cloud1 = loadImage("cloud1.png");
        cloud2 = loadImage("cloud2.png");
        duck = loadImage("duck.png");
        grass = loadImage("grass.png");
        jump = loadImage("jump.png");
        run1 = loadImage("run_anim1.png");
        run2 = loadImage("run_anim2.png");
        run3 = loadImage("run_anim3.png");
        run4 = loadImage("run_anim4.png");
        run5 = loadImage("run_anim5.png");
        selector = loadImage("selector.png");

        skyBlue = new Color(208, 244, 247);

        hit = loadSound("hit.wav");
        onJump = loadSound("onjump.wav");


        Frame f1 = new Frame(run1, .1f);
        Frame f2 = new Frame(run2, .1f);
        Frame f3 = new Frame(run3, .1f);
        Frame f4 = new Frame(run4, .1f);
        Frame f5 = new Frame(run5, .1f);

        runAnimation = new Animation(f1, f2, f3, f4, f5);
    }

    private static AudioClip loadSound(String fileName) {
        URL fileUrl = Resources.class.getClassLoader().getResource("resources/wav/" + fileName);
        return Applet.newAudioClip(fileUrl);
    }

    private static BufferedImage loadImage(String fileName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(Resources.class.getClassLoader()
                    .getResourceAsStream("resources/img/" + fileName));
        } catch (IOException e) {
            System.err.println("Error reading " + fileName);
            e.printStackTrace();
        }
        return img;
    }

}
