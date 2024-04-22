package uk.ac.soton.comp1206.component;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.soton.comp1206.event.BlockClickedListener;
import uk.ac.soton.comp1206.game.Grid;

public class GameBoard extends GridPane {

    private static final Logger logger = LogManager.getLogger(GameBoard.class);

    private final int cols;
    private final int rows;
    private final double width;
    private final double height;
    private final Grid grid;
    private final GameBlock[][] blocks;
    private BlockClickedListener blockClickedListener;

    public GameBoard(Grid grid, double width, double height) {
        this.cols = grid.getCols();
        this.rows = grid.getRows();
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.blocks = new GameBlock[cols][rows];
        build();
    }

    public GameBoard(int cols, int rows, double width, double height) {
        this.cols = cols;
        this.rows = rows;
        this.width = width;
        this.height = height;
        this.grid = new Grid(cols, rows);
        this.blocks = new GameBlock[cols][rows];
        build();
    }

    private void build() {
        logger.info("Building grid: {} x {}", cols, rows);
        setMaxWidth(width);
        setMaxHeight(height);
        setGridLinesVisible(true);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                createBlock(x, y);
            }
        }
    }

    private void createBlock(int x, int y) {
        double blockWidth = width / cols;
        double blockHeight = height / rows;
        GameBlock block = new GameBlock(this, x, y, blockWidth, blockHeight);
        add(block, x, y);
        blocks[x][y] = block;
        block.bind(grid.getGridProperty(x, y));
        block.setOnMouseClicked(event -> blockClicked(event, block));
    }

    private void blockClicked(MouseEvent event, GameBlock block) {
        logger.info("Block clicked: {}", block);
        if (blockClickedListener != null) {
            blockClickedListener.blockClicked(block);
        }
    }

    public void setOnBlockClick(@SuppressWarnings("exports") EventHandler<MouseEvent> handler) {
        // Iterate through all blocks in the grid and register the event handler
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                GameBlock block = blocks[x][y];
                block.setOnMouseClicked(handler);
            }
        }
    }

    public void setOnBlockClick(BlockClickedListener listener) {
        this.blockClickedListener = listener;
    }

    public GameBlock getBlock(int x, int y) {
        return blocks[x][y];
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public void fadeOutBlocks(Set<GameBlock> blocks) {
        for (GameBlock block : blocks) {
            block.fadeOut();
        }
    }
}
