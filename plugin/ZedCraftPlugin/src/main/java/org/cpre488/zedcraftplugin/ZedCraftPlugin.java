package org.cpre488.zedcraftplugin;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZedCraftPlugin extends JavaPlugin implements Listener {

    public static ZedCraftPlugin main;

    @Override
    public void onEnable() {
        main = this;
        // Hello friends this is Trevor :)
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "ZedCraft Server starting up...");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Boot success!");

        // Registers the event we created
        this.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Server shutting down...");
    }
}
