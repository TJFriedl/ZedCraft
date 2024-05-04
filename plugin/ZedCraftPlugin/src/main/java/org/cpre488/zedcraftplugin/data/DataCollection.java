package org.cpre488.zedcraftplugin.data;

import org.bukkit.ChatColor;
import org.cpre488.zedcraftplugin.Main;
import org.cpre488.zedcraftplugin.classes.BlockData;
import org.cpre488.zedcraftplugin.classes.RGBA;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataCollection {


    public static HashMap<String, BlockData> populateJSONMap() throws IOException {

        //Set up the variables needed for the calculations in this code
        HashMap<String, BlockData> map = new HashMap<>();
        JsonReader reader = Json.createReader(new FileReader(Main.main.getDataFolder() + "//blocks.json"));
        JsonArray jsonArray = reader.readArray();

        //Start the logic to the loop
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject obj = jsonArray.getJsonObject(i);

            // Extract data from JSON object
            String textureName = obj.getString("name");
            System.out.println(textureName);
            JsonArray array = obj.getJsonArray("averageRGBA");
            RGBA rgba = new RGBA((short) array.getInt(0), (short) array.getInt(1),
                    (short) array.getInt(2), (short) array.getInt(3));
            String materialName = obj.getString("MaterialName");
            String blockData = obj.getString("blockData");

            // Append data to the HashSet
            map.put(textureName, new BlockData(rgba, materialName, Integer.parseInt(blockData)));
        }

        Main.main.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[ZedCraft] Blocks HashMap Populated.");
        return map;

    }

}
