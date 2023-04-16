package Talban.FlappyBoard;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PImage;

public class StatusBar extends GameObject{

    // Determine the max length of the bar. It is default to a quarter of the screen width
    private float barLength;

    // The left X coordinate of the bar
    private float barX;

    // The top coordinate of the bar
    private float barY;

    // A width variable that adjusts to the bar length depending on how many points are acquired
    private float barComplete;

    // Image of the truck on the loading bar
    private static PImage truck;

    public StatusBar(PApplet sketch) {
        super(sketch);
        // The length of the bar is a third of the screen
        barLength = sketch.width * 0.25f;
        // The x-coordinate of the bars top left
        barX = sketch.width / 2f - barLength / 2f;
        // The y-coordinate of the bars top left
        barY = 20f;
        // Load the image of the truck
        truck = JImage.getImage(sketch, "/Talban/FlappyBoard/Sprites/Amazon_Truck.png");
    }

    // Set barComplete to a ratio according to how many pipes have been passed to the goal.
    public void update() {
        barComplete = ((float)Player.points / C.pipesPassedToWin) * (sketch.width * 0.25f);
    }

    public void display() {
        // Draw the grey shadow bar
        sketch.noStroke();
        sketch.fill(160);
        sketch.rect(barX - 2f, 20f - 3f, sketch.width * 0.25f, barY);

        // Draw the background bar
        sketch.fill(170);
        sketch.rect(barX, 20f, sketch.width * 0.25f, barY);

        // Draw the filled in bar showing the percentage completion
        sketch.fill(0, 168, 225);
        sketch.rect(barX, 20f, barComplete, barY);

        // Draw Truck
        sketch.image(truck, barX + barComplete - 60, barY / 2);

        // Display Points
        sketch.textSize(17);
        sketch.fill(50);
        sketch.textAlign(sketch.RIGHT, sketch.TOP);

        // Show the amount of pipes passed
        sketch.text(Player.points, barX + barLength, barY);
    }

}
