package me.luucx7.listeners;

import me.luucx7.core.MaxHealthHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent ev) {
        MaxHealthHandler.applyHealthChange(ev.getPlayer());
    }
}
