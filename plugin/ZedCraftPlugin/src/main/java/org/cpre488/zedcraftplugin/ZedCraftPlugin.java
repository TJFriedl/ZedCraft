package org.cpre488.zedcraftplugin;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZedCraftPlugin extends JavaPlugin implements Listener {

    public static ZedCraftPlugin main;

    @Override
    public void onEnable() {
        main = this;
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "ZedCraft Server starting up...");

        // Do everything we need to do in order for the plugin to be configured
        try {
            //Configure the plugins first
            this.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        } catch (Exception e) {
            getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "ERROR upon plugin startup");
            throw new RuntimeException(e);
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Boot success!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Server shutting down...");
    }
}
