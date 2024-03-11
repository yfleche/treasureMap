package map.treasure;

import map.treasure.item.AbstractMapItem;
import map.treasure.item.Adventurer;
import map.treasure.item.Map;
import map.treasure.item.Mountain;
import map.treasure.item.Treasure;
import map.treasure.utils.Coordinate;
import map.treasure.utils.Orientation;
import map.treasure.utils.Reader;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReaderTest {

    Reader reader = new Reader();


    @Test
    public void shouldThrowIfCoordinateAreOutsideTheMap() throws FileNotFoundException {
        //GIVEN
        String item = "A - 3 - 4 - S - AAAAA";
        Map map = new Map(3, 4);
        Coordinate maximumCoordinate = new Coordinate(3, 4);
        //WHEN
        Executable executable = () -> reader.lineReader2(item, map, maximumCoordinate);
        //THEN
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void shouldThrowIfCardFormatIsNotRespected() throws FileNotFoundException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("card0.txt");
        assertThrows(IllegalStateException.class, () -> reader.mapReader(url.getPath()));
    }

    @Test
    public void shouldThrowAnExceptionIfTheMapIsSquare() throws FileNotFoundException {
        //GIVEN
        URL url = Thread.currentThread().getContextClassLoader().getResource("card0_Square.txt");
        //WHEN
        Executable executable = () -> reader.mapReader(url.getPath());
        //THEN
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void mapReader() throws FileNotFoundException {
        //GIVEN
        URL url = Thread.currentThread().getContextClassLoader().getResource("card1.txt");
        Mountain mountain = new Mountain(new Coordinate(1, 1));
        Mountain mountain2 = new Mountain(new Coordinate(2, 2));
        List<Mountain> mountains = Arrays.asList(mountain, mountain2);
        Treasure treasure = new Treasure(new Coordinate(0, 3), 2);
        Treasure treasure2 = new Treasure(new Coordinate(1, 3), 1);
        List<Treasure> treasures = Arrays.asList(treasure, treasure2);
        //WHEN
        Map map = reader.mapReader(url.getPath());
        //THEN
        assertEquals(3, map.width());
        assertEquals(4, map.height());
        assertEquals(mountains, map.mountains());
        assertEquals(treasures, map.treasures());
        assertEquals(Arrays.asList(), map.adventurers());

    }

    @Test
    public void mapReader2() throws FileNotFoundException {
        //GIVEN
        URL url = Thread.currentThread().getContextClassLoader().getResource("card2.txt");
        Mountain mountain = new Mountain(new Coordinate(1, 0));
        Mountain mountain2 = new Mountain(new Coordinate(2, 1));
        List<Mountain> mountains = Arrays.asList(mountain, mountain2);
        Treasure treasure = new Treasure(new Coordinate(0, 3), 2);
        Treasure treasure2 = new Treasure(new Coordinate(1, 3), 3);
        List<Treasure> treasures = Arrays.asList(treasure, treasure2);
        Adventurer adventurer = new Adventurer("Lara", new Coordinate(1, 1), Orientation.S, "AADADAGGA");
        List<Adventurer> adventurers = Arrays.asList(adventurer);
        //WHEN
        Map map = reader.mapReader(url.getPath());
        //THEN
        assertEquals(3, map.width());
        assertEquals(4, map.height());
        assertEquals(mountains, map.mountains());
        assertEquals(treasures, map.treasures());
        assertEquals(adventurers, map.adventurers());

    }

}