package map.treasure;

import map.treasure.item.Map;
import map.treasure.utils.Reader;
import map.treasure.utils.Writer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class TreasureMap {
    static String PATH_TO_FILES = "src/main/resources/";

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        Writer writer = new Writer();
        if(args.length < 1){
            System.out.println("The name of the file has to be the first argument of the program and has to be in "+ PATH_TO_FILES);
            return;
        }
        try{
            String[] split = args[0].split(".txt");
            if(split.length != 1 || !args[0].endsWith(".txt")){
                System.out.println("The extension of the file has to be '.txt'");
                return;
            }
            String fileName = split[0];
            Map map = reader.mapReader(PATH_TO_FILES + fileName + ".txt");
            map.playAdventure();
            writer.mapWriter(map, PATH_TO_FILES + fileName + "_resultat.txt");
        }
        catch (FileNotFoundException e){
            System.out.println("The file " + args[0] + " is not in the " + PATH_TO_FILES);
        }

    }
}
