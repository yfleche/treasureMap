package map.treasure.item;

import map.treasure.utils.Coordinate;
import map.treasure.utils.Orientation;

import java.util.Objects;
import java.util.StringJoiner;

public class Adventurer extends Treasure{
    private final String name;

    private Orientation orientation;

    private final String movements;

    public Adventurer(String name, Coordinate coordinate, Orientation orientation, String movements) {
        super(coordinate, 0);
        this.name = name;
        this.orientation = orientation;
        this.movements = movements;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public String getMovements() {
        return movements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Adventurer that = (Adventurer) o;
        return Objects.equals(name, that.name) && orientation == that.orientation && Objects.equals(movements, that.movements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, orientation, movements);
    }

    public void turnToLeft(){
        switch (orientation){
            case N -> this.orientation = Orientation.O;
            case E -> this.orientation = Orientation.N;
            case S -> this.orientation = Orientation.E;
            case O -> this.orientation = Orientation.S;
        }
    }

    public void turnToRight(){
        switch (orientation){
            case N -> this.orientation = Orientation.E;
            case E -> this.orientation = Orientation.S;
            case S -> this.orientation = Orientation.O;
            case O -> this.orientation = Orientation.N;
        }
    }


    public void anAdventurerFoundATreasure(){
        setNumber(getNumber() + 1);
    }

    @Override
    public String toString() {
        return new StringJoiner(" - ")
                .add("A").add(name)
                .add(getCoordinate().toString())
                .add(orientation.toString())
                .add(String.valueOf(getNumber())).toString();
    }
}
