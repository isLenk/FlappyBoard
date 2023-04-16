package Talban.FlappyBoard;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.ArrayList;

public class GameRunner extends PApplet {
    // Player object
    private Player player;

    // Lists for walls, backgroundObjects, and pipes
    private ArrayList<GameObject> walls;
    private ArrayList<BackgroundObject> backgroundObjects;
    private ArrayList<Pipe> pipes;

    // Font for death text and points
    private PFont gameFont;

    // Timer for when the next pipe gets added in
    private int nextPipeDelay;

    // Tick for when the death screen appears after death
    private int deathDelay;

    // Status bar object, the thing on the top of your screen
    private StatusBar statusBar;

    // Results object, for when the game ends
    private Results results;

    // Set window height
    public void settings(){
        size(1280, 720, P2D);
    }

    public void setup() {
        textFont(createFont("Microsoft Sans Serif", 40));
        frameRate(60);
        // Create player object
        player = new Player(this, C.playerPositionX, height - C.wallThickness - 65, 65, 48, 0, 5, color(179, 142, 48), -1);

        // Initialize walls object list
        walls = new ArrayList<>();

        // Initialize background objects list
        backgroundObjects = new ArrayList<>();

        // Initialize pipes
        pipes = new ArrayList<>();

        // Create status bar object
        statusBar = new StatusBar(this);

        // Create results object
        results = new Results(this);

        // Create wall game object
        GameObject wall = new GameObject(this, 0, height - C.wallThickness, width, C.wallThickness, 0, 0, color(150,150,150), -1);

        // Add floor
        walls.add(wall);

        // Reset tick delay
        nextPipeDelay = C.nextPipeTickDelay;
        deathDelay = C.deathDelay;

        // Reset game status'
        C.gameOver = false;
        C.gameComplete = false;

        // Add in three background clouds
        for (int i = 0; i < 3; i++) {
            backgroundObjects.add(new BackgroundObject(this, "/Talban/FlappyBoard/Sprites/Clouds/Cloud.png"));
        }

        // Add a starter pipe
        pipes.add(new Pipe(this));
    }



    public void draw() {
        // Variable to store pipe index.
        int currentPipeIndex = 0;

        // Repeat until the current pipe index passes the last pipe
        while (currentPipeIndex < pipes.size()) {
            // Temporary variable to store the current pipe
            Pipe pipe = pipes.get(currentPipeIndex);

            // Update the pipe and remove the pipe if it is dead
            pipe.update(player);
            if (pipe.isDead()) {
                pipes.remove(pipe);
            }

            // Increment pipe index
            currentPipeIndex++;
        }

        player.update(walls);
        statusBar.update();

        background(220,220,220);

        // When the player earns enough points, complete the game
        if (Player.points >= C.pipesPassedToWin) {
            C.gameComplete = true;
        }

        // Subtract one from the pipe delay
        nextPipeDelay--;

        // Reset tick delay and add new pipe
        if (nextPipeDelay <= 0 && !C.gameComplete) {
            nextPipeDelay = C.nextPipeTickDelay;
            pipes.add(new Pipe(this));
        }

        // Update and display background objects
        for (BackgroundObject backgroundObject : backgroundObjects) {
            backgroundObject.update();
            backgroundObject.display();
        }

        // Display wall objects
        for (GameObject wall : walls) {
            wall.display();
        }

        // Display pipe objects
        for (Pipe pipe : pipes) {
            pipe.display();
        }

        // Display players and status bars
        player.display();
        statusBar.display();

        // When the game ends, start death tick delay
        if (C.gameOver || C.gameComplete) {
            deathDelay --;

            if (deathDelay <= 0) {
                results.display();
            }
        }


    }

    public void keyPressed() {
        // Call the player keyPressed method
        player.keyPressed();

        // Restart the game
        if (keyCode == 'R' && C.gameOver) {
            setup();
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = {""};
        GameRunner mySketch = new GameRunner();
        PApplet.runSketch(processingArgs, mySketch);
    }

}
