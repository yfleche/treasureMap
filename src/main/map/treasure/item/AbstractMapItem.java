package map.treasure.item;

import map.treasure.utils.Coordinate;

import java.util.Objects;

public abstract class AbstractMapItem implements IMapObject {
    private Coordinate coordinate;

    public AbstractMapItem(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractMapItem that = (AbstractMapItem) o;
        return Objects.equals(coordinate, that.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }
}
