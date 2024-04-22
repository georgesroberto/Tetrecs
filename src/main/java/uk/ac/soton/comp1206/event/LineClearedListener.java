package uk.ac.soton.comp1206.event;
import uk.ac.soton.comp1206.component.GameBlockCoordinates;


import java.util.Set;

public interface LineClearedListener {
    void onLineCleared(Set<GameBlockCoordinates> clearedBlocks);
}