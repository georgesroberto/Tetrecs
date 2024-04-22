package uk.ac.soton.comp1206.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Grid {
    private final int cols;
    private final int rows;
    private final SimpleIntegerProperty[][] grid;
   
    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        //Create the grid itself
        grid = new SimpleIntegerProperty[cols][rows];

        //Add a SimpleIntegerProperty to every block in the grid
        for(var y = 0; y < rows; y++) {
            for(var x = 0; x < cols; x++) {
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
        try {
            //Get the value held in the property at the x and y index provided
            return grid[x][y].get();
        } catch (ArrayIndexOutOfBoundsException e) {
            //No such index
            return -1;
        }
    }


    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public boolean canPlayPiece(int x, int y, GamePiece piece) {
        // Get the shape of the piece
        int[][] shape = piece.getBlocks();

        // Check if the piece can fit within the grid boundaries
        if (x < 0 || y < 0 || x + shape.length > cols || y + shape[0].length > rows) {
            return false;
        }

        // Check if any cells that the piece would occupy are already occupied
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 0 && grid[x + i][y + j].get() != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public void playPiece(int x, int y, GamePiece piece) {
        // Get the shape of the piece
        int[][] shape = piece.getBlocks();

        // Place the piece onto the grid
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 0) {
                    grid[x + i][y + j].set(piece.getValue());
                }
            }
        }
    }

    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    public boolean isBlockFilled(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isBlockFilled'");
    }

}
