package map.treasure.item;

import map.treasure.utils.Coordinate;

public class Mountain extends AbstractMapItem {
    public Mountain(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public String toString() {
        return "M - " + getCoordinate();
    }
}
