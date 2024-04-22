package uk.ac.soton.comp1206.scene;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.ui.GameWindow;
import javafx.util.Pair;

import java.io.*;

public class ScoresScene extends BaseScene {

    private static final Logger logger = LogManager.getLogger(ScoresScene.class);

    private final SimpleListProperty<Pair<String, Integer>> localScores = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final File scoresFile = new File("scores.txt");    
    private Pane root;

    @SuppressWarnings("exports")
    public ScoresScene(GameWindow gameWindow, Pane root) {
        super(gameWindow);
        setRoot(root);
    }

    @Override
    public void build() {
        logger.info("Building Scores Scene");

        getRoot().getStyleClass().add("menu-background");

        // Load scores from file
        loadScores();

        // Add scores list
        ListView<Pair<String, Integer>> scoresListView = new ListView<>();
        scoresListView.setItems(localScores);
        getRoot().getChildren().add(scoresListView);

        // Display title
        Text title = new Text("High Scores");
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/style/Orbitron-ExtraBold.ttf"), 48));
        VBox titleBox = new VBox(title);
        titleBox.getStyleClass().add("menu-title");
        getRoot().getChildren().add(titleBox);
    }

    // Getter and setter methods for root pane
    @SuppressWarnings("exports")
    public Pane getRoot() {
        return root;
    }

    public void setRoot(@SuppressWarnings("exports") Pane root) {
        this.root = root;
    }

    private void loadScores() {
        logger.info("Loading scores from file");

        try (BufferedReader reader = new BufferedReader(new FileReader(scoresFile))) {
            String line;
            ObservableList<Pair<String, Integer>> scores = FXCollections.observableArrayList();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                scores.add(new Pair<>(name, score));
            }

            localScores.set(scores);

        } catch (IOException e) {
            logger.error("Error loading scores from file: " + e.getMessage());
        }
    }

    private void writeScores() {
        logger.info("Writing scores to file");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoresFile))) {
            for (Pair<String, Integer> score : localScores.get()) {
                writer.write(score.getKey() + ":" + score.getValue() + "\n");
            }
        } catch (IOException e) {
            logger.error("Error writing scores to file: " + e.getMessage());
        }
    }

    public void addHighScore(int score) {
        String name = ""; // Prompt user for name
        Pair<String, Integer> newScore = new Pair<>(name, score);

        // Insert new score at correct position
        for (int i = 0; i < localScores.size(); i++) {
            if (score > localScores.get(i).getValue()) {
                localScores.add(i, newScore);
                return;
            }
        }

        // If score is not higher than any existing score, add it to the end
        localScores.add(newScore);
    }
    
    public void saveScores() {
        writeScores();
    }

    @Override
    public void initialise() {
        // Reveal high scores
        // Add any additional initialization logic here
    }

}
