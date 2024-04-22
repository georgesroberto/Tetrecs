package uk.ac.soton.comp1206.game;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.Pair;

public class ScoresList extends ListView<Pair<String, Integer>> {

    public ScoresList(ObservableList<Pair<String, Integer>> items) {
        super(items);
        setCellFactory(new Callback<ListView<Pair<String, Integer>>, ListCell<Pair<String, Integer>>>() {
            @Override
            public ListCell<Pair<String, Integer>> call(ListView<Pair<String, Integer>> param) {
                return new ScoreCell();
            }
        });
    }

    /**
     * Custom ListCell to display score entries
     */
    private class ScoreCell extends ListCell<Pair<String, Integer>> {
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
