package org.cpre488.zedcraftplugin.camera;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cpre488.zedcraftplugin.Main;
import org.cpre488.zedcraftplugin.data.DataCollection;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
            if (args.length != 1)
                player.sendMessage(ChatColor.RED + "[ZedCraft] Incorrect format: /picture {File Name}");

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
        }

        return false;
    }

    private void HandleImageIO(Player player, String imageName) throws IOException {
        BufferedImage image = ImageIO.read(new File(Main.main.getDataFolder() + "//Pictures//" + imageName));
        int width = image.getWidth();
        int height = image.getHeight();
        HashMap<String, Material> blockMap = new HashMap<>();

        int taskID = Bukkit.getScheduler().runTaskTimer(Main.main, () -> {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Map.Entry<String, Material> entry = CameraLogic.findClosestBlock(image.getRGB(x, y));
                    blockMap.put(entry.getKey(), entry.getValue());
                    //player.sendMessage(entry.toString());
                }
            }
        }, 0L, 10L).getTaskId();
        Bukkit.getScheduler().runTaskLater(Main.main, () -> Bukkit.getScheduler().cancelTask(taskID),
                (long) width * height / 2);
    }
}
