package uk.ac.soton.comp1206.component;

import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.util.Duration;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GameBlock extends Canvas {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(GameBlock.class);
    @SuppressWarnings("exports")
    public static final Color[] COLOURS = {
            Color.TRANSPARENT,
            Color.DEEPPINK,
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.YELLOWGREEN,
            Color.LIME,
            Color.GREEN,
            Color.DARKGREEN,
            Color.DARKTURQUOISE,
            Color.DEEPSKYBLUE,
            Color.AQUA,
            Color.AQUAMARINE,
            Color.BLUE,
            Color.MEDIUMPURPLE,
            Color.PURPLE
    };

    private final GameBoard gameBoard;
    private final double width;
    private final double height;
    private final int x;
    private final int y;
    private final IntegerProperty value = new SimpleIntegerProperty(0);

    public GameBlock(GameBoard gameBoard, int x, int y, double width, double height) {
        this.gameBoard = gameBoard;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        setWidth(width);
        setHeight(height);

        paint();

        value.addListener(this::updateValue);
    }


    private void updateValue(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        paint();
    }


    public void paint() {
        if (value.get() == 0) {
            paintEmpty();
        } else {
            paintFilled(COLOURS[value.get()]);
            if (isMiddleSquare()) {
                drawCircle();
            }
        }
    }

    private boolean isMiddleSquare() {
        return getX() == (gameBoard.getCols() - 1) / 2 && getY() == (gameBoard.getRows() - 1) / 2;
    }

    private void drawCircle() {
        GraphicsContext gc = getGraphicsContext2D();
        double blockSize = getWidth();
        double centerX = blockSize / 2;
        double centerY = blockSize / 2;
        double radius = blockSize / 4; // Adjust circle radius as needed
        gc.setFill(Color.RED); // Set circle color
        gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return this.value.get();
    }

    public void bind(ObservableValue<? extends Number> input) {
        value.bind(input);
    }

    public void setFilled(boolean filled) {
        value.set(filled ? 1 : 0);
    }

    private void paintEmpty() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
    
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeRect(0, 0, width, height);
    }

    private void paintFilled(Color colour) {
        GraphicsContext gc = getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        LinearGradient gradient = new LinearGradient(0, 0, 0, height, false, CycleMethod.NO_CYCLE,
                new Stop(0.0, colour.deriveColor(1, 1, 1, 0.5)),
                new Stop(1.0, colour.deriveColor(1, 1, 1, 0.8)));

        gc.setFill(gradient);
        gc.fillRect(0, 0, width, height);

        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeRect(0, 0, width, height);
    }

    // Additional methods for event handling and animations can be added here

    public void fadeOutBlocks(Set<GameBlock> blocks) {
        for (GameBlock block : blocks) {
            block.fadeOut();
        }
    }

    public void fadeOut() {
        // Implement the logic to fade out the GameBlock
        // For example, you might change the opacity of the block over time
        // You can use JavaFX animations or transitions to achieve the fade-out effect
        // For instance, if GameBlock extends javafx.scene.shape.Rectangle
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), this);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }
}
