package uk.ac.soton.comp1206.scene;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.ui.GamePane;
import uk.ac.soton.comp1206.ui.GameWindow;
import uk.ac.soton.comp1206.Multimedia;


/**
 * The main menu of the game. Provides a gateway to the rest of the game.
 */
public class MenuScene extends BaseScene {

    private static final Logger logger = LogManager.getLogger(MenuScene.class);

    // Create a Multimedia instance
    private final Multimedia multimedia = new Multimedia();

    private final Stage stage;

    /**
     * Create a new menu scene
     * @param stage the stage to display the menu in
     * @param gameWindow the Game Window this will be displayed in
     */
    public MenuScene(@SuppressWarnings("exports") Stage stage, GameWindow gameWindow) {
        super(gameWindow);
        this.stage = stage;
        logger.info("Creating Menu Scene");
    }

    /**
     * Build the menu layout
     */
    @Override
    public void build() {
        logger.info("Building " + this.getClass().getName());

        root = new GamePane(gameWindow.getWidth(),gameWindow.getHeight());

        var menuPane = new StackPane();
        menuPane.setMaxWidth(gameWindow.getWidth());
        menuPane.setMaxHeight(gameWindow.getHeight());
        menuPane.getStyleClass().add("menu-background");
        root.getChildren().add(menuPane);

        var mainPane = new BorderPane();
        menuPane.getChildren().add(mainPane);

        // Set background image
        Image backgroundImage = new Image("background.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        mainPane.setBackground(new Background(background));

        // Title text
        var title = new Text("TetrECS");
        title.getStyleClass().add("title");
        mainPane.setTop(title);

        // Play button with image
        Image playButtonImage = new Image("play_button.png");
        Button playButton = new Button("", new ImageView(playButtonImage));
        playButton.setOnAction(this::startGame);
        mainPane.setCenter(playButton);

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(this::exitGame);
        mainPane.setBottom(exitButton);

        // Play background music
        multimedia.playMusic();
    }

    /**
     * Initialise the menu
     */
    @Override
    public void initialise() {

    }

    /**
     * Handle when the Start Game button is pressed
     * @param event event
     */
    private void startGame(ActionEvent event) {
        gameWindow.startChallenge();
    }

    /**
     * Handle when the Exit Game button is pressed
     * @param event event
     */
    private void exitGame(ActionEvent event) {
        stage.close(); // Close the stage
    }

}
