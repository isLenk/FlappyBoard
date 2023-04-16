package Talban.FlappyBoard;

import processing.core.PApplet;
import processing.core.PImage;
import Talban.FlappyBoard.JImage;
import java.util.ArrayList;
import java.util.Random;

public class Pipe extends GameObject {
    // Random to get a random position for the openingPositionY variable
    private Random rng;

    // The center of the gap opening for both pipes
    private float openingPositionY;

    // How wide the gap is between the top and bottom pipe
    private float pipeGapLength;

    // Sets to true when a point has been awarded, the player will no longer be able to get a point from the pipe
    private boolean pointAwarded = false;

    // Sets to true when out of screen, this will determine if it gets removed from the pipes list next draw
    private boolean isDead = false;

    // When the pipe has not been passed yet and the player is still alive
    private PImage topActiveImage = JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Pipe/activetop.png");

    // When the player has passed through the pipe and received the point
    private PImage topInactiveImage = JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Pipe/inactivetop.png");

    // When the player dies
    private PImage topDeadImage = JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Pipe/deadtop.png");

    // When the player passes the final required pipe
    private PImage topWinImage = JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Pipe/wintop.png");

    // The image for the body of the pipe
    private PImage bodyImage = JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Pipe/Body.png");

    public Pipe(PApplet sketch)                                           {
        super(sketch);
        // Creates random object
        rng = new Random();

        // Sets X to the right side of the screen
        x = sketch.width;

        // Sets a random gap length with a range offset by the min and max range
        pipeGapLength = C.minPipeGap + rng.nextFloat() * (C.maxPipeGap - C.minPipeGap);

        // Sets a random opening position in the range of the screen height and top and bottom gap ranges
        openingPositionY = rng.nextInt(sketch.height - C.pipeGapBottomLimit - C.pipeGapTopLimit) + C.pipeGapTopLimit;

        // Sets x velocity to the constant pipe velocity
        xVelocity = C.pipeVelocity;
    }

    public float topPipe() {
        return openingPositionY - pipeGapLength/2;
    }

    public float bottomPipe() {
        return openingPositionY + pipeGapLength/2;
    }

    public boolean isDead() {
        return isDead;
    }

    public void update(Player player) {

        // Continue sliding to the left if the game is still playing
        if (!C.gameOver) {x += xVelocity;}

        // If the player has entered the pipes left X and has not yet passed the right side of the pipe
        if (player.right() >= x && player.left() < x + C.pipeThickness) {
            // If the players Y coordinate is not inside the pipe gap
            if (player.top() < topPipe() || player.bottom() > bottomPipe()) {
                player.death();
            };
        }

        // When the player passes the pipe and the game isn't complete, award them
        if (player.left() >= x + C.pipeThickness && !pointAwarded && !C.gameComplete) {
            pointAwarded = true;
            player.point();
        }

        // Expand the gap length if either two conditions are met
        if (pointAwarded || C.gameComplete) {
            pipeGapLength += 20;
        }

        // Set 'isDead' to true when pipe exits screen
        if (x + C.pipeThickness < 0) {
            isDead = true;
        }
    }

    public void display() {

        // Loop to repeatedly draw the top of the pipe body
        for (int i = 0; i < 30 +topPipe()/30; i++) {
            // 30 is the height of the pipe image and 15 is an offset so that we can draw the head image
            sketch.image(bodyImage, x, topPipe() - (30*i) - 15);
        }


        // Loop to repeatedly draw the bottom pipe body
        for (int i = 0; i < ((sketch.height - bottomPipe() )/30); i++) {
            sketch.image(bodyImage, x, bottomPipe() + (30*i));
        }

        // Changes pipe colour to red when dead
        if(C.gameOver) {
            sketch.image(topDeadImage, x, topPipe());
            sketch.image(topDeadImage, x, bottomPipe() - 15);
        }

        // Changes pipe colour to yellow upon game complete
        else if (C.gameComplete) {
            sketch.image(topWinImage, x, topPipe());
            sketch.image(topWinImage, x, bottomPipe() - 15);
        }

        // Changes pipe colour to green upon awarded point
        else if (pointAwarded) {
            sketch.image(topInactiveImage, x, topPipe());
            sketch.image(topInactiveImage, x, bottomPipe() - 15);
        }

        // Default pipe colour to blue
        else {
            sketch.image(topActiveImage, x, topPipe());
            sketch.image(topActiveImage, x, bottomPipe() - 15);
        }
    }


}
