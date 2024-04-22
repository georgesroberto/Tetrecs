package uk.ac.soton.comp1206.scene;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import uk.ac.soton.comp1206.ui.GamePane;
import uk.ac.soton.comp1206.ui.GameWindow;

/**
 * BaseScene serves as a blueprint for creating scenes in the application.
 */
public abstract class BaseScene {

    /** Reference to the GameWindow. */
    protected final GameWindow gameWindow;

    /** Root node of the scene's layout. */
    protected GamePane root;

    /** JavaFX Scene object associated with the scene. */
    protected Scene scene;

    /**
     * Initializes the BaseScene with a reference to the GameWindow.
     * @param gameWindow the GameWindow associated with the scene
     */
    public BaseScene(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    /**
     * Performs initialization tasks for the scene.
     */
    public abstract void initialise();

    /**
     * Builds the scene's layout and components.
     */
    public abstract void build();

    @SuppressWarnings("exports")
    public Scene setScene() {
        Scene previous = gameWindow.getScene();
        Scene scene = new Scene(root, previous.getWidth(), previous.getHeight(), Color.BLACK);
        scene.getStylesheets().add(getClass().getResource("/style/game.css").toExternalForm());
        this.scene = scene;
        return scene;
    }

    @SuppressWarnings("exports")
    public Scene getScene() {
        return this.scene;
    }

}
