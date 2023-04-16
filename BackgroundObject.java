package Talban.FlappyBoard;

import processing.core.PApplet;
import processing.core.PImage;
import Talban.FlappyBoard.JImage;

import java.util.Random;
// creating backround object class
public class BackgroundObject extends GameObject {

    // Random to give variance to the game objects
    private Random rng;

    // Holds the image of the background object
    private static PImage img;

    // The pause time before regenerating the background object
    private int regenerationDelay;
    // cloud width, hieght and regeneration rate
    public BackgroundObject(PApplet sketch, String objectImage) {
        super(sketch);
        rng = new Random();
        // Store the object image
        img = JImage.getImage(sketch, objectImage);
        regenerationDelay = 0;
        // x speed
        xVelocity = -4;
        // y speed
        yVelocity = 0;
        x = sketch.width + rectWidth + rng.nextInt(700);;
        y = rng.nextInt(500);
    }
    // repositions clouds to right side of screen
    public void reset() {
        // sets random velocity
        xVelocity = -1 * (rng.nextInt(C.maxBackgroundObjectVelocity) + 3);
        // resets regenration delay
        regenerationDelay = 0;
        // moves clouds to right side of screen with a random offset of 500
        x = sketch.width + rectWidth + rng.nextInt(500);
        // moves clouds y position from 100 to 500 pixels down from the top
        y = rng.nextInt(400) + 100;
    }

    public void update() {
        // stops generating clouds when game ends
        if (C.gameOver) {
            return;
        }
        // adds pause before generating next cloud
        if (regenerationDelay > 0) {
            regenerationDelay--;
            if (regenerationDelay <= 0) {
                reset();
            }
            return;
        }

        // starting x and y velocity
        x += xVelocity;
        y += yVelocity;
        // random regeration delay
        if (x < -200 && regenerationDelay == 0) {
            regenerationDelay = rng.nextInt(100) + 2;
        }
    }

    public void display() {
        sketch.fill(backgroundColour);
        sketch.stroke(borderColour);
        sketch.image(img, x, y);
    }
}
