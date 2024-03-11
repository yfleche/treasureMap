package map.treasure.utils;

import java.util.Comparator;

public record Coordinate(int horizontalPosition, int verticalPosition) implements Comparable<Coordinate> {

    @Override
    public String toString() {
        return horizontalPosition + " - " + verticalPosition;
    }

    public Coordinate nextPosition(Orientation orientation) {
        return switch (orientation) {
            case N -> new Coordinate(horizontalPosition, verticalPosition - 1);
            case S -> new Coordinate(horizontalPosition, verticalPosition + 1);
            case E -> new Coordinate(horizontalPosition + 1, verticalPosition);
            case O -> new Coordinate(horizontalPosition - 1, verticalPosition);
        };
    }

    @Override
    public int compareTo(Coordinate otherCoordinate) {
        if (horizontalPosition == otherCoordinate.horizontalPosition && verticalPosition == otherCoordinate.verticalPosition) {
            return 0;
        }
        if (horizontalPosition <= otherCoordinate.horizontalPosition || verticalPosition <= otherCoordinate.verticalPosition)
            return -1;
        return 1;
    }
}
