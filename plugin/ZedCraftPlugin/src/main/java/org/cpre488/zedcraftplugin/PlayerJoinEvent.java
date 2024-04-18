package org.cpre488.zedcraftplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.cpre488.zedcraftplugin.ZedCraftPlugin.main;

public class PlayerJoinEvent implements Listener {

    // Example event handler for player join event
    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        main.getServer().getConsoleSender().sendMessage("WAGOOGUS");
        Player player = e.getPlayer();
        player.sendMessage(ChatColor.GOLD + "You are currently playing Minecraft on Java 1.8 on a ZedBoard FPGA :)");
    }
}
