package uk.ac.soton.comp1206.component;

import uk.ac.soton.comp1206.game.GamePiece;

public class PieceBoard extends GameBoard {

    public PieceBoard(double width, double height) {
        super(3, 3, width, height); // Create a 3x3 grid for the PieceBoard
    }

    public void setPiece(GamePiece piece) {
        // Get the blocks of the piece
        int[][] pieceBlocks = piece.getBlocks();

        // Iterate over the blocks in the PieceBoard
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                // Check if the current block is within the bounds of the piece
                if (x < pieceBlocks.length && y < pieceBlocks[0].length) {
                    // Convert the integer value to boolean to represent the filled state
                    boolean isFilled = pieceBlocks[x][y] != 0;
                    // Set the appearance of the block based on whether the corresponding block in the piece is filled
                    getBlock(x, y).setFilled(isFilled);
                } else {
                    // If the current block is outside the bounds of the piece, clear it
                    getBlock(x, y).setFilled(false);
                }
            }
        }
    }

}
