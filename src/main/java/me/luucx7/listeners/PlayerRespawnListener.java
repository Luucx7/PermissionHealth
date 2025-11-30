package me.luucx7.listeners;

import me.luucx7.core.MaxHealthHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
    @EventHandler
    public void playerRespawn(PlayerRespawnEvent ev) { MaxHealthHandler.applyHealthChange(ev.getPlayer()); }
}
