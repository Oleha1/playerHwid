package com.oleha.storage;

import org.bukkit.plugin.Plugin;

public class phStorage {
    private final Plugin plugin;

    public phStorage(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        this.plugin.getLogger().info("playerHardWare создан!");
    }
    public void addPlayerHardWare(int hwid, String name) {
        String path = String.join(".","playerHardWare",String.valueOf(hwid),"userName");
        this.plugin.getConfig().set(path,name);
        this.plugin.saveConfig();
    }
    public void removePlayerHardWare(int hwid) {
        String path = String.join(".","playerHardWare",String.valueOf(hwid));
        this.plugin.getConfig().set(path,null);
        this.plugin.saveConfig();
    }
    public boolean isPlayer(int hwid) {
        return this.plugin.getConfig().get("playerHardWare." + hwid) != null;
    }
    public String getPlayerNameByHardWare(int hwid) {
        return this.plugin.getConfig().get("playerHardWare." + hwid + ".userName").toString();
    }
    public int getHardWareByPlayerName(String name) {
        int id = -1;
        for (int i = 0; i < 100000; i++) {
            if (this.plugin.getConfig().get("playerHardWare." + i) != null) {
                if (this.plugin.getConfig().get("playerHardWare." + i + ".userName").equals(name)) {
                    id = i;
                }
            }
        }
        return id;
    }
}
