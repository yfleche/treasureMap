package map.treasure.utils;

import map.treasure.item.AbstractMapItem;
import map.treasure.item.Adventurer;
import map.treasure.item.Map;
import map.treasure.item.Mountain;
import map.treasure.item.Treasure;
import map.treasure.utils.Coordinate;
import map.treasure.utils.Orientation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

    public Map mapReader(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner input = new Scanner(file);
        String[] mapDescription = input.nextLine().split(" - ");
        if (!mapDescription[0].equals("C"))
            throw new IllegalStateException("Impossible de récupérer les informations de la carte, le format devrait être: C - Largeur - Longeur");
        if (mapDescription[1].equals(mapDescription[2]))
            throw new IllegalArgumentException("La carte doit etre rectangulaire");
        Map map = new Map(Integer.parseInt(mapDescription[1]), Integer.parseInt(mapDescription[2]));
        while (input.hasNext()) {
            String nextLine = input.nextLine();
            lineReader(nextLine, map);
        }

        input.close();
        return map;

    }

    private String checkAdventurerActions(String actions) {
        boolean areActionsLegit = Pattern.matches("[AGD]*", actions);
        if (!areActionsLegit) {
            throw new IllegalArgumentException("Les actions ne sont pas valides, les mouvements possible sont A (Avancer), G (tourner à Gauche) et D (tourner à Droite)");
        }
        return actions;
    }

    private void lineReader(String line, Map map) {
        String[] array = line.split(" - ");

        switch (array[0].charAt(0)) {
            case 'M' -> map.mountains().add(new Mountain(createCoordinateFromString(array[1], array[2])));
            case 'T' ->
                    map.treasures().add(new Treasure(createCoordinateFromString(array[1], array[2]), Integer.parseInt(array[3])));
            case 'A' ->
                    map.adventurers().add(new Adventurer(array[1], createCoordinateFromString(array[2], array[3]), getOrientationFromString(array[4]), checkAdventurerActions(array[5])));
            case '#' -> {
                return;
            }
            default -> throw new IllegalStateException("Unexpected value: " + array[0]);
        }
    }

    public void lineReader2(String line, Map map, Coordinate mapMaximumCoordinate) {
        String[] array = line.split(" - ");

        AbstractMapItem abstractMapItem = switch (array[0].charAt(0)) {
            case 'M' -> new Mountain(createCoordinateFromString(array[1], array[2]));
            case 'T' -> new Treasure(createCoordinateFromString(array[1], array[2]), Integer.parseInt(array[3]));
            case 'A' -> new Adventurer(array[1], createCoordinateFromString(array[2], array[3]), getOrientationFromString(array[4]), array[5]);
            case '#' -> null;
            default -> throw new IllegalStateException("Unexpected value: " + array[0]);
        };

        if (abstractMapItem != null)
            map.add(abstractMapItem, mapMaximumCoordinate);
    }

    private Coordinate createCoordinateFromString(String horizontal, String vertical) {
        return new Coordinate(Integer.parseInt(horizontal), Integer.parseInt(vertical));
    }

    private Orientation getOrientationFromString(String orientation) {
        return switch (orientation) {
            case "N" -> Orientation.N;
            case "S" -> Orientation.S;
            case "E" -> Orientation.E;
            case "O" -> Orientation.O;
            default -> throw new IllegalStateException("Unexpected value: " + orientation);
        };
    }
}
