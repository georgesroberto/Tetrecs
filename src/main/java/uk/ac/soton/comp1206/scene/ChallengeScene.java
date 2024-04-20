package uk.ac.soton.comp1206.scene;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.component.GameBlock;
import uk.ac.soton.comp1206.component.GameBoard;
import uk.ac.soton.comp1206.game.Game;
import uk.ac.soton.comp1206.ui.GamePane;
import uk.ac.soton.comp1206.ui.GameWindow;
import uk.ac.soton.comp1206.Multimedia;

import uk.ac.soton.comp1206.component.PieceBoard; // Import the PieceBoard class

/**
 * The Single Player challenge scene. Holds the UI for the single player challenge mode in the game.
 */
public class ChallengeScene extends BaseScene {

    private static final Logger logger = LogManager.getLogger(MenuScene.class);
    protected Game game;

    private final Multimedia multimedia = new Multimedia();

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

        root = new GamePane(gameWindow.getWidth(),gameWindow.getHeight());

        var challengePane = new StackPane();
        challengePane.setMaxWidth(gameWindow.getWidth());
        challengePane.setMaxHeight(gameWindow.getHeight());
        challengePane.getStyleClass().add("menu-background");
        root.getChildren().add(challengePane);

        var mainPane = new BorderPane();
        challengePane.getChildren().add(mainPane);

        var board = new GameBoard(game.getGrid(),gameWindow.getWidth()/2,gameWindow.getWidth()/2);
        mainPane.setCenter(board);

        //Handle block on gameboard grid being clicked
        board.setOnBlockClick(this::blockClicked);

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
        var pieceBoard = new PieceBoard(gameWindow.getWidth()/4, gameWindow.getHeight()/4); // Adjust the size as needed
        mainPane.setLeft(pieceBoard);
    
        // Play background music
        multimedia.playMusic(); 
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

        // Start new game
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

}
