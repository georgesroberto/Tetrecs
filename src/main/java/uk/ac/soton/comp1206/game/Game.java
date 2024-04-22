package uk.ac.soton.comp1206.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.component.GameBlock;
import uk.ac.soton.comp1206.component.GameBlockCoordinate;
import uk.ac.soton.comp1206.event.LineClearedListener;
import uk.ac.soton.comp1206.event.NextPieceListener;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;


public class Game {

    private static final Logger logger = LogManager.getLogger(Game.class);

    protected final int rows;
    protected final int cols;
    protected final Grid grid;
    private GamePiece currentPiece;
    private int totalScore;

    private NextPieceListener nextPieceListener;
    private Set<LineClearedListener> lineClearedListeners = new HashSet<>();
    private ScheduledExecutorService gameLoopExecutor;

    private final IntegerProperty score;
    private final IntegerProperty level;
    private final IntegerProperty lives;
    private final IntegerProperty multiplier;
    
    public Game(int cols, int rows) { 
        this.cols = cols;
        this.rows = rows;
        
        // Create a new grid model to represent the game state
        this.grid = new Grid(cols, rows);
        
        gameLoopExecutor = Executors.newSingleThreadScheduledExecutor();
        
        // Initialize properties with default values
        this.score = new SimpleIntegerProperty(0);
        this.level = new SimpleIntegerProperty(1);
        this.lives = new SimpleIntegerProperty(3);
        this.multiplier = new SimpleIntegerProperty(1);

        // Initialize total score and multiplier
        this.totalScore = 0;
    }

    public void start() {
        logger.info("Starting game");
        initialiseGame();
        startGameLoop();
    }

    public void initialiseGame() {
        logger.info("Initialising game");
        spawnPiece();
    }

    public void spawnPiece() {
        currentPiece = generateAndPlacePiece();
    }

    public void nextPiece() {
        currentPiece = generateAndPlacePiece();
    }

    private GamePiece generateAndPlacePiece() {
        Random random = new Random();
        GamePiece newPiece = GamePiece.createPiece(random.nextInt(GamePiece.PIECES));

        // Place the new piece in the middle of the top row (for a 5x5 grid)
        int startX = (getCols() - newPiece.getBlocks().length) / 2;
        int startY = 0;

        if (grid.canPlayPiece(startX, startY, newPiece)) {
            grid.playPiece(startX, startY, newPiece);
        } else {
            // Handle game over condition if the piece cannot be placed
            endGame();
            resetBoard();
            System.out.println("Game Over - Unable to place piece");
        }

        return newPiece;
    }

    private void resetBoard() {
        // Perform actions to reset the board
        logger.info("Resetting board...");
        grid.clear();
        score.set(0); // Reset score
        level.set(1); // Reset level
        lives.set(3); // Reset lives
        multiplier.set(1); // Reset multiplier
    }

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

    public void afterPiece() {
        score(linesCleared(), blocksCleared());

        if (linesCleared() > 0) {

            multiplier.set(multiplier.get() + 1);
        } else {
            multiplier.set(1);
        }

        level.set(totalScore / 1000 + 1);
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void score(int linesCleared, int blocksCleared) {
        int score = linesCleared * blocksCleared * 10 * multiplier.get();
        totalScore += score;
        this.score.set(totalScore);
    }

    public int linesCleared() {
        int clearedLines = 0;
        for (int y = 0; y < getRows(); y++) {
            boolean lineCleared = true;
            for (int x = 0; x < getCols(); x++) {
                if (!grid.isBlockFilled(x, y)) {
                    lineCleared = false;
                    break;
                }
            }
            if (lineCleared) {
                clearedLines++;
            }
        }
        return clearedLines;
    }

    public int blocksCleared() {
        int clearedBlocks = 0;
        for (int y = 0; y < getRows(); y++) {
            for (int x = 0; x < getCols(); x++) {
                if (grid.isBlockFilled(x, y)) {
                    clearedBlocks++;
                }
            }
        }
        return clearedBlocks;
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

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public int getCurrentLevel() {
        return level.get();
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

    public void setNextPieceListener(NextPieceListener listener) {
        this.nextPieceListener = listener;
    }

    private void generateNextPiece() {
        Random random = new Random();
        GamePiece nextPiece = GamePiece.createPiece(random.nextInt(GamePiece.PIECES));
        notifyNextPieceListener(nextPiece); // Notify the next piece listener
    }

    private void notifyNextPieceListener(GamePiece piece) {
        if (nextPieceListener != null) {
            nextPieceListener.nextPiece(piece);
        }
    }

    public void addLineClearedListener(LineClearedListener listener) {
        lineClearedListeners.add(listener);
    }

    private void notifyLineClearedListeners(Set<GameBlockCoordinate> clearedBlocks) {
        for (LineClearedListener listener : lineClearedListeners) {
            listener.onLineCleared(clearedBlocks);
        }
    }

    public int getTimerDelay() {
        // Calculate the delay at the maximum of either 2500 milliseconds or 12000 - 500 * the current level
        return Math.max(2500, 12000 - (500 * getCurrentLevel()));
    }

    public void startGameLoop() {
        int delay = getTimerDelay();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameLoop(); // Call the game loop method
            }
        }, 0, delay);
    }

    // Implement the game loop method
    private void gameLoop() {
        loseLife();
        discardCurrentPiece();
        startGameLoop();

        if (getLives() < 0) {
            endGame(); 
        }
    }

    private void loseLife() {
        setLives(getLives() - 1);
        if (getLives() <= 0) {
            endGame();
        }
    }

    private void discardCurrentPiece() {
        setCurrentPiece(null);
    }

    private void endGame() {
        stopGameLoop();
        logger.info("Game over!");
    }

    public void setCurrentPiece(GamePiece piece) {
        this.currentPiece = piece;
    }
    
    private void stopGameLoop() {
        if (gameLoopExecutor != null && !gameLoopExecutor.isShutdown()) {
            gameLoopExecutor.shutdown();
            try {
                gameLoopExecutor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
