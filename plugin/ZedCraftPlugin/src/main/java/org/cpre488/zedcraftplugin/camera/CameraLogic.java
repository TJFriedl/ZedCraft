package org.cpre488.zedcraftplugin.camera;

import org.bukkit.Material;
import org.cpre488.zedcraftplugin.Main;
import org.cpre488.zedcraftplugin.classes.RGBA;

import java.util.AbstractMap;
import java.util.Map;

public class CameraLogic {

    /**
     * Current implementation of "findClosestMatch" coming from artifacts package. Takes rgba value from singular image
     * pixel and approximates a block to be returned to a player
     * @param rgba - rgba valu
     * @return
     */
    public static Map.Entry<String, Material> findClosestBlock(int rgba) {
        if (((rgba >> 24) & 0xFF) == 0)
            return new AbstractMap.SimpleEntry<>("Air", Material.AIR);

        double shortestDist = Double.MAX_VALUE;
        String block = "air";

        for (Map.Entry<String, RGBA> entry : Main.blocks.entrySet()) {
            String blockName = entry.getKey();
            double dist = getDist(rgba, entry);
            if (dist < shortestDist) {
                shortestDist = dist;
                block = blockName;
            }
        }

        //Need some logic for outstanding blocks
        Material mat = Material.valueOf(block.toUpperCase());
        return new AbstractMap<>.SimpleEntry<>(block.toUpperCase(), mat);
    }

    private Map.Entry

    private static double getDist(int rgba, Map.Entry<String, RGBA> entry) {
        RGBA blockColor = entry.getValue();

        // Dist equation equivalent: (rgba.getFoo - blockColor.getFoo)^2
        double alphaDist = Math.pow(((rgba >> 24) & 0xFF) - blockColor.getAlpha(), 2);
        double redDist = Math.pow(((rgba >> 16) & 0xFF) - blockColor.getRed(), 2);
        double greenDist = Math.pow(((rgba >> 8) & 0xFF) - blockColor.getGreen(), 2);
        double blueDist = Math.pow((rgba & 0xFF) - blockColor.getBlue(), 2);

        double dist = Math.sqrt(alphaDist + redDist + greenDist + blueDist);
        return dist;
    }

}
