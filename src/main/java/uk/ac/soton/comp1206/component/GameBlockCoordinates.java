package uk.ac.soton.comp1206.component;

import javafx.beans.NamedArg;

import java.util.Objects;

public class GameBlockCoordinates {
    private final int x;
    private final int y;
    private int hash;

    public GameBlockCoordinates(@NamedArg("x") int x, @NamedArg("y") int y) {
        this.x = x;
        this.y = y;
        this.hash = calculateHash();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameBlockCoordinates add(int x, int y) {
        return new GameBlockCoordinates(this.x + x, this.y + y);
    }

    public GameBlockCoordinates add(GameBlockCoordinates other) {
        return add(other.getX(), other.getY());
    }

    public GameBlockCoordinates subtract(int x, int y) {
        return new GameBlockCoordinates(this.x - x, this.y - y);
    }

    public GameBlockCoordinates subtract(GameBlockCoordinates other) {
        return subtract(other.getX(), other.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GameBlockCoordinates other = (GameBlockCoordinates) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    private int calculateHash() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "GameBlockCoordinate [x = " + x + ", y = " + y + "]";
    }
}
