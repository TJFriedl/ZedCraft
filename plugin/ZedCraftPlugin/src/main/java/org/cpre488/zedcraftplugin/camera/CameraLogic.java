package org.cpre488.zedcraftplugin.camera;

import org.cpre488.zedcraftplugin.Main;
import org.cpre488.zedcraftplugin.classes.BlockData;
import org.cpre488.zedcraftplugin.classes.RGBA;

import java.util.Map;

public class CameraLogic {

    /**
     * Current implementation of "findClosestMatch" coming from artifacts package. Takes rgba value from singular image
     * pixel and approximates a block to be returned to a player
     * @param rgba - rgba value
     * @return
     */
    public static BlockData findClosestBlock(int rgba) {
        if (((rgba >> 24) & 0xFF) == 0) return Main.blocks.get("air");

        double shortestDist = Double.MAX_VALUE;
        String block = "air";

        for (Map.Entry<String, BlockData> entry : Main.blocks.entrySet()) {
            double dist = getDist(rgba, entry.getValue().getRgba());
            if (dist < shortestDist) {
                shortestDist = dist;
                block = entry.getKey();
            }
        }

        return Main.blocks.get(block);
    }

    private static double getDist(int rgba, RGBA blockColor) {

        // Dist equation equivalent: (rgba.getFoo - blockColor.getFoo)^2
        double alphaDist = Math.pow(((rgba >> 24) & 0xFF) - blockColor.getAlpha(), 2);
        double redDist = Math.pow(((rgba >> 16) & 0xFF) - blockColor.getRed(), 2);
        double greenDist = Math.pow(((rgba >> 8) & 0xFF) - blockColor.getGreen(), 2);
        double blueDist = Math.pow((rgba & 0xFF) - blockColor.getBlue(), 2);

        return Math.sqrt(alphaDist + redDist + greenDist + blueDist);
    }

}
