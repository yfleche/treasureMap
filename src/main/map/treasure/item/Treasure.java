package map.treasure.item;

import map.treasure.utils.Coordinate;

import java.util.Objects;

public class Treasure extends AbstractMapItem {
    private int number;

    public Treasure(Coordinate coordinate, int number) {
        super(coordinate);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void anAdventurerFoundATreasure() {
        number--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Treasure treasure = (Treasure) o;
        return number == treasure.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number);
    }

    @Override
    public String toString() {
        return number == 0 ? "" : "T - " + getCoordinate() + " - " + number;
    }
}
