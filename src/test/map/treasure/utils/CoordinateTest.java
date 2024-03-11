package map.treasure.utils;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {

    @ParameterizedTest
    @CsvSource({"3, 4", "2, 2", "10, 10", "1, 7"})
    void testToString(int horizontal, int vertical) {
        //GIVEN
        Coordinate coordinate = new Coordinate(horizontal, vertical);
        //WHEN
        String str = coordinate.toString();
        //THEN
        assertEquals(horizontal + " - " + vertical, str);
    }

    @ParameterizedTest
    @CsvSource({"N, 3, 3", "E, 4, 4", "O, 2, 4", "S, 3, 5"})
    void nextPosition(Orientation orientation, int vertical, int horizontal) {
        //GIVEN
        Coordinate coordinate = new Coordinate(3, 4);
        //WHEN
        Coordinate newCoordinate = coordinate.nextPosition(orientation);
        //THEN
        assertEquals(vertical + " - " + horizontal, newCoordinate.toString());
    }

    @ParameterizedTest
    @CsvSource({"2, 3", "1, 2", "0, 3", "0, 0", "2, 0"})
    void shouldBeInferior(int horizontal, int vertical) {
        //GIVEN
        Coordinate coordinate = new Coordinate(3, 4);
        //WHEN
        boolean compare = coordinate.compareTo(new Coordinate(horizontal, vertical)) > 0;
        //THEN
        assertTrue(compare);
    }

    @Test
    public void shouldBeEqual() {
        //GIVEN
        Coordinate coordinate = new Coordinate(3, 4);
        //WHEN
        boolean compare = coordinate.compareTo(coordinate) == 0;
        //THEN
        assertTrue( compare);
    }

    @ParameterizedTest
    @CsvSource({"3, 4", "7, 9", "4, 4", "3, 3", "10, 10"})
    void shouldBeSuperiorOrEqual(int horizontal, int vertical) {
        //GIVEN
        Coordinate coordinate = new Coordinate(3, 4);
        //WHEN
        int tmp = coordinate.compareTo(new Coordinate(horizontal, vertical));
        boolean compare = tmp <= 0;
        //THEN
        assertTrue(compare);
    }
}