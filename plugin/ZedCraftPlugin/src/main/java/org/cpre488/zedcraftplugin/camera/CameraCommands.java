package org.cpre488.zedcraftplugin.camera;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

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
            File picture = new File("ZedCraft//Pictures//" + args[0]);
            if (!picture.exists()) {
                player.sendMessage(ChatColor.RED + "[ZedCraft] File does not exist. Choose from below:");
                for (File file : new File("ZedCraft//Pictures//").listFiles()) {
                    player.sendMessage(ChatColor.GOLD + file.getName());
                }
            } else {
                player.sendMessage(ChatColor.GREEN + "[ZedCraft] This file exists.");
            }
            return true;
        }

        return false;
    }
}
