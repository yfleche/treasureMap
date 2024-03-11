package map.treasure.item;

import map.treasure.utils.Coordinate;
import map.treasure.utils.Orientation;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {

    @ParameterizedTest
    @CsvSource({"0, 0, N", "2, 3, S", "2, 3, E", "0, 0, O"})
    public void shouldNotMoveTheAdventurer_whenHeWantsToGoOutsideTheMap(int horizontal, int vertical, Orientation orientation) {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(horizontal, vertical);
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, orientation, "A");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4, new ArrayList<>(), new ArrayList<>(), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        Assertions.assertEquals(adventurerInitalCoordinate, map.adventurers().get(0).getCoordinate());
    }

    @ParameterizedTest
    @CsvSource({"N", "S", "E", "O"})
    public void shouldMoveTheAdventurer_whenHeDoesNotHaveAnObstacle(Orientation orientation) {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(1, 1);
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, orientation, "A");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4, new ArrayList<>(), new ArrayList<>(), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        Assertions.assertEquals(adventurerInitalCoordinate.nextPosition(orientation), map.adventurers().get(0).getCoordinate());
    }

    @ParameterizedTest
    @CsvSource({"N, O", "S, E", "E, N", "O, S"})
    public void shouldChangeDirectionOfTheAdventurerToTheLeft(Orientation firstOrientation, Orientation finalOrientation) {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(1, 1);
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, firstOrientation, "G");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4, new ArrayList<>(), new ArrayList<>(), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        assertEquals(finalOrientation, map.adventurers().get(0).getOrientation());
    }

    @ParameterizedTest
    @CsvSource({"N, E", "S, O", "E, S", "O, N"})
    public void shouldChangeDirectionOfTheAdventurerToTheRight(Orientation firstOrientation, Orientation finalOrientation) {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(1, 1);
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, firstOrientation, "D");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4, new ArrayList<>(), new ArrayList<>(), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        assertEquals(finalOrientation, map.adventurers().get(0).getOrientation());
    }

    @ParameterizedTest
    @CsvSource({"0, 0, S, 0, 3", "0, 3, N, 0, 0", "2, 0, O, 0, 0", "0, 0, E, 2, 0"})
    public void shouldCrossTheMap(int firstHorizontalPosition, int firstVerticalPosition, Orientation orientation, int finalHorizontalPosition, int finalVerticalPosition) {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(firstHorizontalPosition, firstVerticalPosition);
        Coordinate finalPosition = new Coordinate(finalHorizontalPosition, finalVerticalPosition);
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, orientation, "AAAAAAA");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4, new ArrayList<>(), new ArrayList<>(), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        Assertions.assertEquals(finalPosition, map.adventurers().get(0).getCoordinate());
    }

    @Test
    public void shouldMakeTheTurnOfTheMapByRight()  {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(0, 0);
        Coordinate finalPosition = new Coordinate(0, 0);
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, Orientation.E, "AAAAAAADAAAAAAADAAAAAAADAAAAAAA");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4, new ArrayList<>(), new ArrayList<>(), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        Assertions.assertEquals(finalPosition, map.adventurers().get(0).getCoordinate());
    }

    @Test
    public void shouldMakeTheTurnOfTheMapByLeft()  {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(0, 0);
        Coordinate finalPosition = new Coordinate(0, 0);
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, Orientation.S, "AAAAAAAGAAAAAAAGAAAAAAAGAAAAAAA");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4, new ArrayList<>(), new ArrayList<>(), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        Assertions.assertEquals(finalPosition, map.adventurers().get(0).getCoordinate());
    }

    @ParameterizedTest
    @CsvSource({"2, 1, E", "1, 2, S", "1, 0, N", "0, 1, O"})
    public void shouldBeBlockByMountain(int mountainHorizontalPosition, int mountainVerticalPosition, Orientation orientation)  {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(1, 1);
        Coordinate finalPosition = new Coordinate(1, 1);
        Mountain mountain = new Mountain(new Coordinate(mountainHorizontalPosition, mountainVerticalPosition));
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, orientation, "AAAAAAA");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4, new ArrayList<>(), List.of(mountain), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        Assertions.assertEquals(finalPosition, map.adventurers().get(0).getCoordinate());
    }

    @Test
    public void shouldNotBeBlockByMountain()  {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(1, 1);
        Coordinate finalPosition = new Coordinate(1, 2);
        Mountain mountain = new Mountain(new Coordinate(1, 0));
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, Orientation.S, "A");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4, new ArrayList<>(), List.of(mountain), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        Assertions.assertEquals(finalPosition, map.adventurers().get(0).getCoordinate());
    }

    @ParameterizedTest
    @CsvSource({"2, 1, E", "1, 2, S", "1, 0, N", "0, 1, O"})
    public void shouldRetrieveATreasure(int treasureHorizontalPosition, int treasureVerticalPosition, Orientation orientation)  {
        //GIVEN
        Coordinate adventurerInitalCoordinate = new Coordinate(1, 1);
        Coordinate finalPosition = new Coordinate(treasureHorizontalPosition, treasureVerticalPosition);
        Treasure treasure = new Treasure(new Coordinate(treasureHorizontalPosition, treasureVerticalPosition), 1);
        Adventurer adventurer = new Adventurer("Lara", adventurerInitalCoordinate, orientation, "A");
        List<Adventurer> adventurers = List.of(adventurer);
        Map map = new Map(3, 4,new LinkedList<>(List.of(treasure)), new ArrayList<>(), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        Assertions.assertEquals(finalPosition, map.adventurers().get(0).getCoordinate());
        assertEquals(1, map.adventurers().get(0).getNumber());
    }


    @ParameterizedTest
    @CsvSource({
            "0, 0, E, 2, 0, O, 1, 0, 2, 0",
            "2, 0, O, 0, 0, E, 1, 0, 0, 0",
            "0, 0, S, 0, 3, N, 0, 1, 0, 2",
            "0, 3, N, 0, 0, S, 0, 2, 0, 1"
    })
    public void adventurerShouldNotGoInTheSameCase(
            int laraHorizontalPosition, int laraVerticalPosition, Orientation laraOrientation,
            int matthewHorizontalPosition, int matthewVerticalPosition, Orientation matthewOrientation,
            int laraFinalHorizontalPosition, int laraFinalVerticalPosition,
            int matthewFinalHorizontalPosition, int matthewFinalVerticalPosition
    ) {
        //GIVEN
        Coordinate laraCoordinate = new Coordinate(laraHorizontalPosition, laraVerticalPosition);
        Coordinate matthewCoordinate = new Coordinate(matthewHorizontalPosition, matthewVerticalPosition);
        Coordinate laraFinalCoordinate = new Coordinate(laraFinalHorizontalPosition, laraFinalVerticalPosition);
        Coordinate matthewFinalCoordinate = new Coordinate(matthewFinalHorizontalPosition, matthewFinalVerticalPosition);
        Adventurer lara = new Adventurer("Lara", laraCoordinate, laraOrientation, "AAAAAA");
        Adventurer matthew = new Adventurer("Matthew", matthewCoordinate, matthewOrientation, "AAAAAAAA");
        List<Adventurer> adventurers = List.of(lara, matthew);
        Map map = new Map(3, 4,new ArrayList<>(), new ArrayList<>(), adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        Assertions.assertEquals(laraFinalCoordinate, map.adventurers().get(0).getCoordinate());
        Assertions.assertEquals(matthewFinalCoordinate, map.adventurers().get(1).getCoordinate());
    }

    @Test
    public void shouldPrintTheMap(){
        //GIVEN
        String printedMap =
            """
                C - 3 - 4
                # {M comme Montagne} - {Nom de l'aventurier} - {Axe horizontal}
                M - 1 - 0
                M - 2 - 1
                # {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}
                T - 1 - 3 - 2
                # {A comme Aventurier} - {Nom de l'aventurier} - {Axe horizontal} - {Axe vertical} - {Orientation} - {Nb. trésors ramassés}
                A - Lara - 0 - 3 - S - 3""";
        Mountain mountain = new Mountain(new Coordinate(1, 0));
        Mountain mountain2 = new Mountain(new Coordinate(2, 1));
        List<Mountain> mountains = Arrays.asList(mountain, mountain2);
        Treasure treasure = new Treasure(new Coordinate(0, 3), 2);
        Treasure treasure2 = new Treasure(new Coordinate(1, 3), 3);
        List<Treasure> treasures = new LinkedList<>(Arrays.asList(treasure, treasure2));
        Adventurer adventurer = new Adventurer("Lara", new Coordinate(1, 1), Orientation.S, "AADADAGGA");
        List<Adventurer> adventurers = Arrays.asList(adventurer);
        Map map = new Map(3, 4, treasures, mountains, adventurers);
        //WHEN
        map.playAdventure();
        //THEN
        assertEquals(printedMap, map.toString());

    }



}