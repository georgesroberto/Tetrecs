package uk.ac.soton.comp1206.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.component.GameBlock;
import java.util.Random;

/**
 * The Game class handles the main logic, state and properties of the TetrECS game.
 * Methods to manipulate the game state and to handle actions made by the player should take place inside this class.
 */
public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

    protected final int rows;
    protected final int cols;
    protected final Grid grid;
    private GamePiece currentPiece;
    private int totalScore;

    /**
     * Bindable properties for score, level, lives, and multiplier
     */
    private final IntegerProperty score;
    private final IntegerProperty level;
    private final IntegerProperty lives;
    private final IntegerProperty multiplier;

    /**
     * Create a new game with the specified rows and columns. Creates a corresponding grid model.
     *
     * @param cols number of columns
     * @param rows number of rows
     */
    public Game(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        // Create a new grid model to represent the game state
        this.grid = new Grid(cols, rows);

        // Initialize properties with default values
        this.score = new SimpleIntegerProperty(0);
        this.level = new SimpleIntegerProperty(1);
        this.lives = new SimpleIntegerProperty(3);
        this.multiplier = new SimpleIntegerProperty(1);

        // Initialize total score and multiplier
        this.totalScore = 0;
    }

    /**
     * Start the game
     */
    public void start() {
        logger.info("Starting game");
        initialiseGame();
    }

    /**
     * Initialise a new game and set up anything that needs to be done at the start
     */
    public void initialiseGame() {
        logger.info("Initialising game");
        spawnPiece();
    }

    /**
     * Spawn a new piece and set it as the current piece
     */
    public void spawnPiece() {
        Random random = new Random();
        currentPiece = GamePiece.createPiece(random.nextInt(GamePiece.PIECES));

        // Place the piece in the middle of the top row (for a 5x5 grid)
        int startX = (getCols() - currentPiece.getBlocks().length) / 2;
        int startY = 0;

        if (grid.canPlayPiece(startX, startY, currentPiece)) {
            grid.playPiece(startX, startY, currentPiece);
        } else {
            // Handle game over condition if the piece cannot be placed
            // For example, you can end the game or reset the board
            // Here, we'll just print a message to the console
            System.out.println("Game Over - Unable to place initial piece");
        }
    }

    /**
     * Replace the current piece with a new piece
     */
    public void nextPiece() {
        Random random = new Random();
        currentPiece = GamePiece.createPiece(random.nextInt(GamePiece.PIECES));

        // Place the new piece in the middle of the top row (for a 5x5 grid)
        int startX = (getCols() - currentPiece.getBlocks().length) / 2;
        int startY = 0;

        if (grid.canPlayPiece(startX, startY, currentPiece)) {
            grid.playPiece(startX, startY, currentPiece);
        } else {
            // Handle game over condition if the piece cannot be placed
            // For example, you can end the game or reset the board
            // Here, we'll just print a message to the console
            System.out.println("Game Over - Unable to place next piece");
        }
    }

    /**
     * Handle what should happen when a particular block is clicked
     *
     * @param gameBlock the block that was clicked
     */
    public void blockClicked(GameBlock gameBlock) {
        // Get the position of this block
        int x = gameBlock.getX();
        int y = gameBlock.getY();

        if (grid.canPlayPiece(x, y, currentPiece)) {
            grid.playPiece(x, y, currentPiece);
            nextPiece();
            afterPiece();
        }
    }

    /**
     * Perform any actions that should be done after a piece is played
     */
    public void afterPiece() {
        // Calculate the score based on the number of lines cleared and blocks cleared
        score(linesCleared(), blocksCleared());

        // Check if the current piece cleared any lines
        if (linesCleared() > 0) {
            // Increase the multiplier
            multiplier.set(multiplier.get() + 1);
        } else {
            // Reset the multiplier if the piece didn't clear any lines
            multiplier.set(1);
        }

        // Update the level based on the total score
        level.set(totalScore / 1000 + 1);
    }

    /**
     * Get the total score
     *
     * @return total score
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Calculate the score based on the number of lines cleared and blocks cleared
     *
     * @param linesCleared  number of lines cleared
     * @param blocksCleared number of blocks cleared
     */
    public void score(int linesCleared, int blocksCleared) {
        // Calculate the score based on the provided formula
        int score = linesCleared * blocksCleared * 10 * multiplier.get();
        // Update the total score
        totalScore += score;
        // Update the score property
        this.score.set(totalScore);
    }


    /**
     * Get the number of lines cleared by the current piece
     *
     * @return number of lines cleared
     */
    public int linesCleared() {
        // Implement logic to determine the number of lines cleared by the current piece
        return 0; // Placeholder
    }

    /**
     * Get the number of blocks cleared by the current piece
     *
     * @return number of blocks cleared
     */
    public int blocksCleared() {
        // Implement logic to determine the number of blocks cleared by the current piece
        return 0; // Placeholder
    }

    /**
     * Get the grid
     *
     * @return the grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Get the number of columns
     *
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Get the number of rows
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    // Accessor methods for properties

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public int getLevel() {
        return level.get();
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }

    public int getLives() {
        return lives.get();
    }

    public IntegerProperty livesProperty() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives.set(lives);
    }

     public int getMultiplier() {
        return multiplier.get();
    }

    public IntegerProperty multiplierProperty() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier.set(multiplier);
    }
}
