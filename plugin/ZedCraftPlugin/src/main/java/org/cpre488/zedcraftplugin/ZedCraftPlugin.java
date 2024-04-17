package org.cpre488.zedcraftplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZedCraftPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Hello friends this is Trevor :)
        getServer().getConsoleSender().sendMessage("§6§lZedCraft Server starting up...");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Boot success!");
    }

    // Example event handler for player join event
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Bukkit.getServer().broadcastMessage("Welcome " + ChatColor.AQUA + player.getDisplayName() + ChatColor.RESET + " to ZedCraft!");
        player.sendMessage(ChatColor.GOLD + "You are currently playing Minecraft on Java 1.8 on a ZedBoard FPGA :)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage("§4§lServer shutting down...");
    }
}
