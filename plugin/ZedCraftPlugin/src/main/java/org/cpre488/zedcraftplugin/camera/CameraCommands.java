package org.cpre488.zedcraftplugin.camera;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cpre488.zedcraftplugin.Main;
import org.cpre488.zedcraftplugin.classes.BlockData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CameraCommands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        // Prevents server from executing this command, could cause errors if not caught.
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only in game players can use that command!");
            return true;
        }

        Player player = (Player) sender; //Because of above clause, we know person using command is player.
        if (!(player.isOp())) {
            player.sendMessage(ChatColor.RED + "[ZedCraft] You must have operator privileges to use this command.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("picture")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "[ZedCraft] Incorrect format: /picture {File Name}");
                return false;
            }

            //Eventually will need to change to path from the SD card
            File picture = new File(Main.main.getDataFolder() + "//Pictures//" + args[0]);
            if (!picture.exists()) {
                player.sendMessage(ChatColor.RED + "[ZedCraft] File does not exist. Choose from below:");
                for (File file : Objects.requireNonNull(new File(Main.main.getDataFolder() + "//Pictures//").listFiles())) {
                    player.sendMessage(ChatColor.GOLD + file.getName());
                }
                return true;
            }

            player.sendMessage(ChatColor.GREEN + "[ZedCraft] This file exists.");
            player.sendMessage(ChatColor.ITALIC + "Doing block calculations...");
            try {
                HandleImageIO(player, args[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (cmd.getName().equalsIgnoreCase("giveblockdata")) {
            if (args.length != 2) {
                player.sendMessage(ChatColor.RED + "[ZedCraft] Incorrect format: /giveblockdata" +
                        " {Block enum} {Metadata}");
                return false;
            }

            try {
                String blockEnum = args[0];
                int metaData = Integer.parseInt(args[1]);
                Material material = Material.valueOf(blockEnum);
                ItemStack item = new ItemStack(material, 1, (short) metaData);
                player.getInventory().addItem(item);
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "[ZedCraft] Could not convert colors properly.");
                return false;
            }

        }

        return true;
    }

    private void HandleImageIO(Player player, String imageName) throws IOException {
        BufferedImage image = ImageIO.read(new File(Main.main.getDataFolder() + "//Pictures//" + imageName));
        int width = image.getWidth();
        int height = image.getHeight();
        Location loc = player.getLocation();
        int playerZ = loc.getBlockZ();

            // CODE GO BRRRRRR
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    BlockData block = CameraLogic.findClosestBlock(image.getRGB(x, y));
                    Location blockLocation = new Location(player.getWorld(), x, height-y, playerZ);
                    blockLocation.getBlock().setType(Material.valueOf(block.getMaterialName()));
                    blockLocation.getBlock().setData((byte) block.getMetaData());
                }
            }
    }
}
