package uk.ac.soton.comp1206.scene;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.component.GameBlock;
import uk.ac.soton.comp1206.component.GameBlockCoordinates;
import uk.ac.soton.comp1206.component.GameBoard;
import uk.ac.soton.comp1206.component.GameLoopListener;
import uk.ac.soton.comp1206.component.PieceBoard;
import uk.ac.soton.comp1206.event.LineClearedListener;
import uk.ac.soton.comp1206.game.Game;
import uk.ac.soton.comp1206.ui.GamePane;
import uk.ac.soton.comp1206.ui.GameWindow;
import uk.ac.soton.comp1206.Multimedia;

import java.util.HashSet;
import java.util.Set;

/**
 * The Single Player challenge scene. Holds the UI for the single player challenge mode in the game.
 */
public class ChallengeScene extends BaseScene implements LineClearedListener, GameLoopListener {

    private static final Logger logger = LogManager.getLogger(MenuScene.class);
    protected Game game;

    private final Multimedia multimedia = new Multimedia();

    private GameBoard gameBoard;
    private PieceBoard pieceBoard;

    // UI elements
    private Label scoreLabel;
    private Label levelLabel;
    private Label multiplierLabel;
    private Label livesLabel;

    /**
     * Create a new Single Player challenge scene
     * @param gameWindow the Game Window
     */
    public ChallengeScene(GameWindow gameWindow) {
        super(gameWindow);
        logger.info("Creating Challenge Scene");
    }

    /**
     * Build the Challenge window
     */
    @Override
    public void build() {
        logger.info("Building " + this.getClass().getName());

        setupGame();

        root = new GamePane(gameWindow.getWidth(), gameWindow.getHeight());

        var challengePane = new StackPane();
        challengePane.setMaxWidth(gameWindow.getWidth());
        challengePane.setMaxHeight(gameWindow.getHeight());
        challengePane.getStyleClass().add("menu-background");
        root.getChildren().add(challengePane);

        var mainPane = new BorderPane();
        challengePane.getChildren().add(mainPane);

        // Create and add the GameBoard
        gameBoard = new GameBoard(game.getGrid(), gameWindow.getWidth() / 2, gameWindow.getWidth() / 2);
        mainPane.setCenter(gameBoard);

        // Handle block on gameboard grid being clicked
        gameBoard.setOnBlockClick(this::blockClicked);

        // Create UI elements for displaying score, level, multiplier, and lives
        scoreLabel = new Label("Score: ");
        levelLabel = new Label("Level: ");
        multiplierLabel = new Label("Multiplier: ");
        livesLabel = new Label("Lives: ");

        // Add UI elements to a VBox
        VBox scoreBox = new VBox(scoreLabel, levelLabel, multiplierLabel, livesLabel);
        mainPane.setRight(scoreBox);

        // Bind UI elements to corresponding properties of the Game class
        scoreLabel.textProperty().bind(game.scoreProperty().asString());
        levelLabel.textProperty().bind(game.levelProperty().asString());
        multiplierLabel.textProperty().bind(game.multiplierProperty().asString());
        livesLabel.textProperty().bind(game.livesProperty().asString());

        // Create and add PieceBoard
        pieceBoard = new PieceBoard(gameWindow.getWidth() / 4, gameWindow.getHeight() / 4);
        mainPane.setLeft(pieceBoard);

        // Play background music
        multimedia.playMusic();

        // Register this scene as a LineClearedListener
        game.addLineClearedListener(this);

        // Register this scene as a GameLoopListener
        game.setOnGameLoop(this);
    }

    /**
     * Handle when a block is clicked
     * @param gameBlock the Game Block that was clicked
     */
    private void blockClicked(GameBlock gameBlock) {
        game.blockClicked(gameBlock);
    }

    /**
     * Setup the game object and model
     */
    public void setupGame() {
        logger.info("Starting a new challenge");
        game = new Game(5, 5);
    }

    /**
     * Initialise the scene and start the game
     */
    @Override
    public void initialise() {
        logger.info("Initialising Challenge");
        game.start();
    }

     @Override
    public void onLineCleared(Set<GameBlockCoordinates> clearedBlocks) {
        // Trigger fade-out effect for cleared blocks in the GameBoard
        Set<GameBlock> blocks = new HashSet<>();
        for (GameBlockCoordinates block : clearedBlocks) {
            blocks.add(gameBoard.getBlock(block.getX(), block.getY()));
        }
        gameBoard.fadeOutBlocks(blocks);
    }

    @Override
    public void onGameLoopStart() {
        resetTimers();
        initializeGameObjects();
        updateUI();
    }

    private void updateUI() {
        // Implement the logic to update the UI here
    }

    private void initializeGameObjects() {
        // Implement the logic to initialize game objects here
    }

    private void resetTimers() {
        // Implement the logic to reset timers here
    }


}
