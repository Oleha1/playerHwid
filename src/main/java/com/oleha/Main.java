package com.oleha;

import com.oleha.command.ph;
import com.oleha.storage.phStorage;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    phStorage playerHwidStorage;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Events(this),this);
        getServer().getPluginCommand("ph").setExecutor(new ph(this));
        playerHwidStorage = new phStorage(this);
        playerHwidStorage.setup();
    }
    @Override
    public void onDisable() {
        saveDefaultConfig();
    }
}