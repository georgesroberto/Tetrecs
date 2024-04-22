package uk.ac.soton.comp1206.game;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Pair;

public class ScoresList extends ListView<Pair<String, Integer>> {

    public ScoresList(ObservableList<Pair<String, Integer>> items) {
        super(items);
        setCellFactory(param -> new ScoreCell());
    }

    /**
     * Custom ListCell to display score entries
     */
    private static class ScoreCell extends ListCell<Pair<String, Integer>> {
        @Override
        protected void updateItem(Pair<String, Integer> item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.getKey() + ": " + item.getValue());
            }
        }
    }
}
