package uk.ac.soton.comp1206.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.component.GameBlock;

import java.util.Random;

/**
 * The Game class handles the main logic, state and properties of the TetrECS game. Methods to manipulate the game state
 * and to handle actions made by the player should take place inside this class.
 */


public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

    protected final int rows;
    protected final int cols;
    protected final Grid grid;
    private GamePiece currentPiece;

    public Game(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        this.grid = new Grid(cols,rows);
    }

    public void start() {
        logger.info("Starting game");
        initialiseGame();
    }

    public void initialiseGame() {
        logger.info("Initialising game");
        spawnPiece();
    }

    
    // TODO: Test 
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



    // TODO: End todo



    public void blockClicked(GameBlock gameBlock) {
        int x = gameBlock.getX();
        int y = gameBlock.getY();

        if (grid.canPlayPiece(x, y, currentPiece)) {
            grid.playPiece(x, y, currentPiece);
            nextPiece();
            afterPiece();
        }
    }

    // TODO: End todo

    public void afterPiece() {
        for (int y = 0; y < getRows(); y++) {
            boolean rowFilled = true;

            // Check if the current row is completely filled
            for (int x = 0; x < getCols(); x++) {
                if (grid.get(x, y) == 0) {
                    rowFilled = false;
                    break;
                }
            }

            // If the row is filled, remove it and shift down all rows above it
            if (rowFilled) {
                for (int yy = y; yy > 0; yy--) {
                    for (int x = 0; x < getCols(); x++) {
                        grid.set(x, yy, grid.get(x, yy - 1));
                    }
                }

                // Add a new empty row at the top
                for (int x = 0; x < getCols(); x++) {
                    grid.set(x, 0, 0);
                }

                // Since we shifted down rows, decrement y to recheck the current row
                y--;
            }
        }
    }


    public Grid getGrid() {
        return grid;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
