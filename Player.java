package Talban.FlappyBoard;

import processing.core.PApplet;
import processing.core.PImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static Talban.FlappyBoard.C.gameOver;


public class Player extends GameObject{

    // For player animation
    private int currentFrame = 0;

    // How many images are used in the player animations
    private int maxFrames;

    // How many game frames need to pass before the next animation frame can play
    private int nextFrameTick;

    // Points earned by how many pipes are passed
    public static int points = 0;

    // Is the player still walking on the ground?
    public boolean walking = false;

    // Counter that accelerates down fall
    protected int fallTick;

    // Counter that decelerates jump height as time passes
    protected int jumpTick;

    // Animation Lists ---------------------
    // List of frames for player walking
    ArrayList<PImage> characterWalkFrames;

    // List of frames for character flying
    ArrayList<PImage> characterFlyFrames;

    // List of frames for character dead
    ArrayList<PImage> characterDeathFrames;

    public Player(PApplet sketch, float x, float y, float rectWidth, float rectHeight, float xVelocity, float yVelocity, int backgroundColor, int borderColour) {
        // Call the super constructor
        super(sketch, x, y, rectWidth, rectHeight, xVelocity, yVelocity, backgroundColor, borderColour);

        // Initialize the array lists
        characterWalkFrames = new ArrayList<>();
        characterFlyFrames = new ArrayList<>();
        characterDeathFrames = new ArrayList<>();

        // Reset points to 0
        points = 0;
        maxFrames = 8;
        // Load in the frames
        for (int i = 0; i < maxFrames; i++) {
            characterWalkFrames.add(JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Player Animation/Walk/Frame_" + i + ".png"));
            characterFlyFrames.add(JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Player Animation/Fly/Frame_" + i + ".png"));
            characterDeathFrames.add(JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Player Animation/Death/Frame_" + i + ".png"));
        }

    }

    // Set the death variable to true
    public void death() {
        gameOver = true;
    }


    public void display() {

        // Run the death frames
        if (gameOver) {
            sketch.image(characterDeathFrames.get(currentFrame), x, y);
            return;
        }

        // Run the flight frames
        if (!walking) {
            sketch.image(characterFlyFrames.get(currentFrame), x, y);
        }

        // Run the walking frames
        else {
            sketch.image(characterWalkFrames.get(currentFrame), x, y);
        }
    }

    public void point() {
        points++;
    }

    public void update(ArrayList<GameObject> walls) {

        walking = false;

        nextFrameTick--;
        jumpTick--;

        // Player Animation Handler
        // When the frame delay has been passed, reset the timer and enter next frame
        if (nextFrameTick <= 0) {
            // Set the current frame to the first when the last frame has been passed, otherwise it adds one
            currentFrame = (currentFrame + 1 < maxFrames) ? currentFrame + 1 : 0;

            // Resets animation tick delay
            nextFrameTick = C.playerAnimationTickDelay;
        }

        // JUMPING: If the jump tick is still active, decrease the player Y coordinate
        if (jumpTick > 0) {
            fallTick = 0;
            // A quick formula that uses initial velocity, gravitational acceleration and jumpTick to emulate a non-linear jump
            y -= C.initialFallVelocity + 0.75 * C.gravityAcceleration * jumpTick;
        }
        // There is a 2 tick delay before the player begins their descent
        else if (jumpTick < -2) {
            fallTick++;
            // A formula similar to the previous one which has a smaller constant. There is no terminal velocity.
            y += C.initialFallVelocity + 0.5 * C.gravityAcceleration * fallTick;
        }

        // Ground Collision, set walking to true if they are still on the ground
        for (GameObject wall : walls) {
            if (this.intersect(wall)) {
                walking = true;
                collidesWallY(wall);
            }
        }

        x += xVelocity;

        // Ground collision, set walking to true if they are still on the grounda
        for (GameObject wall : walls) {
            if (this.intersect(wall)) {
                walking = true;
                collidesWallX(wall);
            }
        }

        // If the player falls below the ground, return them to the floor height
        if (y > sketch.height - C.wallThickness) {
            y = sketch.height - C.wallThickness - rectHeight;
        }
    }

    // Reset the jump tick variable
    public void flap() {
        jumpTick = C.playerMaxJumpTick;
    }

    public void keyPressed() {
        // Do not continue if the game has been ended
        if (gameOver) {return;}

        // If the player presses 'Spacebar' then jump.
        if (sketch.keyCode == ' ') {
            flap();
        }
    }
}
