package uk.ac.soton.comp1206.scene;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.ui.GamePane;
import uk.ac.soton.comp1206.ui.GameWindow;
import uk.ac.soton.comp1206.component.PieceBoard;
import uk.ac.soton.comp1206.game.GamePiece;

/**
 * The instructions scene to display the game instructions and all 15 pieces in the game.
 */
public class InstructionsScene extends BaseScene {

    private static final Logger logger = LogManager.getLogger(InstructionsScene.class);

    /**
     * Create a new instructions scene
     * @param gameWindow the Game Window this will be displayed in
     */
    public InstructionsScene(GameWindow gameWindow) {
        super(gameWindow);
        logger.info("Creating Instructions Scene");
    }

    /**
     * Build the instructions layout
     */
    @Override
    public void build() {
        logger.info("Building " + this.getClass().getName());

        // Use a GamePane for dynamic scaling
        root = new GamePane(gameWindow.getWidth(), gameWindow.getHeight());

        var grid = new GridPane();
        ((GamePane) root).getChildren().add(grid);
        grid.setHgap(5);
        grid.setVgap(5);

        // Create and add PieceBoards for each of the 15 game pieces
        int pieceCount = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                GamePiece piece = GamePiece.createPiece(pieceCount++);
                PieceBoard pieceBoard = new PieceBoard(100, 100);
                pieceBoard.setPiece(piece);
                grid.add(pieceBoard, j, i);
            }
        }
    }

    /**
     * Initialise the instructions scene
     */
    @Override
    public void initialise() {
        // Add keyboard listeners
        scene.setOnKeyPressed(this::handleKeyPress);
    }

    /**
     * Handle key presses
     * @param event the key event
     */
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            gameWindow.startMenu(); // Return to the main menu when ESC is pressed
        }
    }
}
