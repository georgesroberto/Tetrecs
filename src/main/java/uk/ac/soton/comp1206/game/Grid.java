package uk.ac.soton.comp1206.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import uk.ac.soton.comp1206.component.GameBlockCoordinates;
import uk.ac.soton.comp1206.event.LineClearedListener;

import java.util.HashSet;
import java.util.Set;

public class Grid {

    private final int cols;
    private final int rows;
    private final SimpleIntegerProperty[][] grid;
    private Set<LineClearedListener> lineClearedListeners = new HashSet<>();


    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        grid = new SimpleIntegerProperty[cols][rows];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                grid[x][y] = new SimpleIntegerProperty(0);
            }
        }
    }

    public IntegerProperty getGridProperty(int x, int y) {
        return grid[x][y];
    }

    public void set(int x, int y, int value) {
        grid[x][y].set(value);
    }

    public int get(int x, int y) {
        if (x < 0 || y < 0 || x >= cols || y >= rows) {
            return -1; // Out of bounds
        }
        return grid[x][y].get();
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public boolean canPlayPiece(int x, int y, GamePiece piece) {
        int[][] shape = piece.getBlocks();
        if (x < 0 || y < 0 || x + shape.length > cols || y + shape[0].length > rows) {
            return false; // Piece out of bounds
        }
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 0 && grid[x + i][y + j].get() != 0) {
                    return false; // Block already occupied
                }
            }
        }
        return true;
    }

    public void playPiece(int x, int y, GamePiece piece) {
        int[][] shape = piece.getBlocks();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 0) {
                    grid[x + i][y + j].set(piece.getValue());
                }
            }
        }
    }

    // Placeholder method: Clears the grid
    public void clear() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                grid[x][y].set(0);
            }
        }
    }

    // Placeholder method: Checks if a block at given coordinates is filled
    public boolean isBlockFilled(int x, int y) {
        if (x < 0 || y < 0 || x >= cols || y >= rows) {
            return false; // Out of bounds, not filled
        }
        return grid[x][y].get() != 0;
    }



    // Method to add a listener
    public void addLineClearedListener(LineClearedListener listener) {
        lineClearedListeners.add(listener);
    }

    // Method to remove a listener (if needed)
    public void removeLineClearedListener(LineClearedListener listener) {
        lineClearedListeners.remove(listener);
    }

    // Method to notify listeners when a line is cleared
    private void notifyLineClearedListeners(Set<GameBlockCoordinates> clearedBlocks) {
        for (LineClearedListener listener : lineClearedListeners) {
            listener.onLineCleared(clearedBlocks);
        }
    }

    public void clearLines() {
        Set<GameBlockCoordinates> clearedBlocks = new HashSet<>();
        notifyLineClearedListeners(clearedBlocks);
    }

}
