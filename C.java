package Talban.FlappyBoard;


public class C {
    /*
    Pallette
        35, 47, 62  Blue
        255, 153, 0 Orange
        19, 26, 34  Black

     */

    // Player Animation Tick Delay
    public static final int playerAnimationTickDelay = 5;

    // The amount of pipes you need to pass in order to beat the level
    public static final int pipesPassedToWin = 30;

    // The thickness of the ground floor
    public static final float wallThickness = 20;

    // The maximum tick a player can jump, used in the jump equation
    public static final int playerMaxJumpTick = 15;

    // The players offset from the left of the screen
    public static final float playerPositionX = 150;

    // How much the player accelerates per tick when free falling and jumping
    public static final float gravityAcceleration = 1.05f;

    // The initial fall velocity of the player
    public static final float initialFallVelocity = 2.14f;

    // The maximum velocity that the background objects can travel.
    public static final int  maxBackgroundObjectVelocity = 5;

    // The X-Velocity that the pipe travels left
    public static final float pipeVelocity = -6;

    // The offset from the top that the pipe can randomly generate a gap
    public static final int pipeGapTopLimit = 100;

    // The offset from the bottom that the pipe can randomly generate a gap
    public static final int pipeGapBottomLimit = 100;

    // The maximum gap opening for a pipe
    public static final float maxPipeGap = 220;

    // The minimum gap opening for a pipe
    public static final float minPipeGap = 200;

    // The thickness of the pipe (Def: 80)
    public static final float pipeThickness = 80;

    // How far away the next pipe will appear by frame. (Def: 75 | 30fps meaning 2.5 seconds)
    public static final int nextPipeTickDelay = 75;

    // How many frames pass before the death screen appears (Def: 60 | 30fps meaning 2 seconds)
    public static final int deathDelay = 60;

    // Is the game over?
    public static boolean gameOver = false;

    // Did the player win?
    public static boolean gameComplete = false;

}
