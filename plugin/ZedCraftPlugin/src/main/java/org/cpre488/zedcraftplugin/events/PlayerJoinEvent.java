package org.cpre488.zedcraftplugin.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    // Example event handler for player join event
    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        e.getPlayer().sendMessage(ChatColor.GOLD + "Welcome, " + ChatColor.AQUA + e.getPlayer().getName() +
                        ChatColor.GOLD + ". You are playing Minecraft on Java 1.8 on a ZedBoard FPGA.");
    }

}
