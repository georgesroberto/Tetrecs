package uk.ac.soton.comp1206.component;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uk.ac.soton.comp1206.game.GamePiece;

public class PieceBoard extends GameBoard {

    public PieceBoard(double width, double height) {
        super(3, 3, width, height); // Assuming PieceBoard is always 3x3
    }

    @Override
    public void draw(@SuppressWarnings("exports") GraphicsContext gc) {
        super.draw(gc);

        // Draw a circle on the middle square
        double centerX = getWidth() / 2;
        double centerY = getHeight() / 2;
        double circleRadius = 20; // Adjust the circle radius as needed
        gc.setFill(Color.BLUE);
        gc.fillOval(centerX - circleRadius, centerY - circleRadius, circleRadius * 2, circleRadius * 2);
    }

    @Override
    public void setPiece(GamePiece piece) {
        int offsetX = (getCols() - piece.getWidth()) / 2;
        int offsetY = (getRows() - piece.getHeight()) / 2;

        super.setPiece(piece, offsetX, offsetY);
    }
}
