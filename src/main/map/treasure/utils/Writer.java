package map.treasure.utils;

import map.treasure.item.Map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public void mapWriter(Map map, String path) throws IOException {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        writer.append(map.toString());

        writer.close();
    }

}
