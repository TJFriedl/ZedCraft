package org.cpre488.zedcraftplugin;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.cpre488.zedcraftplugin.camera.CameraCommands;
import org.cpre488.zedcraftplugin.classes.RGBA;
import org.cpre488.zedcraftplugin.data.DataCollection;
import org.cpre488.zedcraftplugin.events.PlayerJoinEvent;

import java.io.File;
import java.util.HashMap;

public final class Main extends JavaPlugin implements Listener {

    public static Main main;
    public static HashMap<String, RGBA> blocks;

    @Override
    public void onEnable() {
        main = this;
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "ZedCraft Server starting up...");

        // Do everything we need to do in order for the plugin to be configured
        try {
            //Set up folder packages
            File directory = new File(this.getDataFolder().getPath());
            if (!directory.exists()) { directory.mkdirs(); }
            blocks = DataCollection.populateJSONMap();

            //Register in-game commands
            CameraCommands cc = new CameraCommands();
            getCommand("picture").setExecutor(cc);
            getCommand("giveblockdata").setExecutor(cc);

            //Register events down below
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
