package org.cpre488.zedcraftplugin.data;

import org.cpre488.zedcraftplugin.classes.RGBA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataCollection {


    public static HashMap<String, RGBA> populateJSONMap() throws IOException {

        //Set up the variables needed for the calculations in this code
        HashMap<String, RGBA> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("ZedCraft/blocks.json"));

        //Start the logic to the loop
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("{"))
            {
                String name = grabName(br.readLine());
                RGBA rgba = grabRGBA(br.readLine());
                map.put(name, rgba);
            }
        }

        return map;

    }

    public static String grabName(String name) {
        int startIndex = 17;
        int endIndex = name.indexOf('"', startIndex + 1);
        if (endIndex == -1)
            endIndex = name.indexOf(',', startIndex + 1);
        if (endIndex == -1)
            endIndex = name.length();

        return name.substring(startIndex, endIndex);
    }

    public static RGBA grabRGBA(String rgba) {
        String[] values = rgba.substring(rgba.indexOf('[') + 1, rgba.indexOf(']')).split(",");

        short red = Short.parseShort(values[0].trim());
        short green = Short.parseShort(values[1].trim());
        short blue = Short.parseShort(values[2].trim());
        short alpha = Short.parseShort(values[3].trim());

        return new RGBA(red, green, blue, alpha);
    }

}
