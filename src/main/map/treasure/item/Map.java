package map.treasure.item;

import map.treasure.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public record Map(int width, int height, List<Treasure> treasures,
                  List<Mountain> mountains, List<Adventurer> adventurers) {
    public Map(int width, int height) {
        this(width, height, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private static int TURN = 0;

    private void moveAdventurerIfPossible(Adventurer adventurer) {
        Coordinate nexAdventurerPosition = adventurer.getCoordinate().nextPosition(adventurer.getOrientation());
        boolean canMoveForward = switch (adventurer.getOrientation()) {
            case N -> adventurer.getCoordinate().verticalPosition() > 0;
            case S -> adventurer.getCoordinate().verticalPosition() < height - 1;
            case E -> adventurer.getCoordinate().horizontalPosition() < width - 1;
            case O -> adventurer.getCoordinate().horizontalPosition() > 0;
        } && mountains.stream().noneMatch(mountain -> doesAdventurerWillHitAMountain(nexAdventurerPosition, mountain))
                && adventurers.stream().noneMatch(otherAdventurer -> otherAdventurer.getCoordinate().equals(nexAdventurerPosition));

        if (canMoveForward) {
            adventurer.setCoordinate(nexAdventurerPosition);
            Optional<Treasure> optionalTreasure = treasures.stream().filter(treasure -> adventurer.getCoordinate().equals(treasure.getCoordinate())).findAny();
            if (optionalTreasure.isPresent()) {
                Treasure treasure = optionalTreasure.get();
                if (treasure.getNumber() > 0) {
                    treasure.anAdventurerFoundATreasure();
                    if (treasure.getNumber() == 0) {
                        treasures.remove(treasure);
                    }
                    adventurer.anAdventurerFoundATreasure();
                }

            }
        }
    }

    private void adventurerAction(Adventurer adventurer) {
        if (adventurer.getMovements().length() <= TURN) {
            return;
        }
        switch (adventurer.getMovements().charAt(TURN)) {
            case 'A' -> moveAdventurerIfPossible(adventurer);
            case 'D' -> adventurer.turnToRight();
            case 'G' -> adventurer.turnToLeft();
            default -> {
                return;
            } // enum for movement possible
        }
    }


    private boolean doesAdventurerWillHitAMountain(Coordinate adventurerNextPosition, Mountain mountain) {
        return adventurerNextPosition.equals(mountain.getCoordinate());

    }

    public void playAdventure() {
        while (adventurers.stream().anyMatch(adventurer -> adventurer.getMovements().length() > TURN)) {
            adventurers.forEach(this::adventurerAction);
            TURN++;
        }
        TURN = 0;

    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("C - " + width + " - " + height);
        if (!mountains.isEmpty()) {
            stringJoiner.add("# {M comme Montagne} - {Nom de l'aventurier} - {Axe horizontal}")
                    .add(mountains.stream().map(Mountain::toString).collect(Collectors.joining("\n")));
        }
        if (!treasures.isEmpty()) {
            stringJoiner.add("# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}")
                    .add(treasures.stream().map(Treasure::toString).collect(Collectors.joining("\n")));
        }
        if (!adventurers.isEmpty()) {
            stringJoiner.add("# {A comme Aventurier} - {Nom de l'aventurier} - {Axe horizontal} - {Axe vertical} - {Orientation} - {Nb. trésors ramassés}")
                    .add(adventurers.stream().map(Adventurer::toString).collect(Collectors.joining("\n")));
        }
        return stringJoiner.toString();
    }

    public void add(AbstractMapItem item, Coordinate coordinate) {
        if (item.getCoordinate().compareTo(coordinate) < 0) {

            if (item instanceof Treasure treasure) {
                treasures.add(treasure);
            }
            if (item instanceof Mountain mountain) {
                mountains.add(mountain);
            }
            if (item instanceof Adventurer adventurer) {
                adventurers.add(adventurer);
            }
            return;
        }
        throw new IllegalArgumentException("The coordinates are outside the map");
    }

}
