package org.cpre488.zedcraftplugin.peripherals;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.cpre488.zedcraftplugin.Main;
import org.cpre488.zedcraftplugin.classes.BlockData;
import org.cpre488.zedcraftplugin.classes.RGBA;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CameraLogic {

    /**
     * Current implementation of "findClosestMatch" coming from artifacts package. Takes rgba value from singular image
     * pixel and approximates a block to be returned to a player
     * @param rgba - rgba value
     * @return - BlockData object containing TextureName, BlockRGB, and MetaData
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

    public static void HandleImageIO(Player player, String imageName) throws IOException {
        BufferedImage image = ImageIO.read(new File(Main.main.getDataFolder() + "//Pictures//" + imageName));
        int width = image.getWidth();
        int height = image.getHeight();
        Location loc = player.getLocation();

        // CODE GO BRRRRRR
        long start = System.nanoTime();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                BlockData block = CameraLogic.findClosestBlock(image.getRGB(x, y));
                Location blockLocation = new Location(player.getWorld(), loc.getBlockX()+x,
                        loc.getBlockY()+(height-y-1), loc.getBlockZ());
                blockLocation.getBlock().setType(Material.valueOf(block.getMaterialName()));
                blockLocation.getBlock().setData((byte) block.getMetaData());
            }
        }
        long end = System.nanoTime();
        double executionT = (double) (end - start) / 1000000;
        String number = String.format("%.3f", executionT);
        double executionTime = Double.parseDouble(number);
        player.sendMessage("Execution time for " + ChatColor.GOLD + imageName + ChatColor.RESET + ": (" +
                width + "x" + height + " pixels) " + executionTime + "ms");
    }

}
