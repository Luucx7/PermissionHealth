package me.luucx7;

import lombok.Getter;
import me.luucx7.listeners.PlayerJoinListener;
import me.luucx7.luckperms.LuckPermsPermissionListener;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MaxHealthPlugin extends JavaPlugin {

    public static final String PERMISSION = "maxhealth";

    @Getter
    private static MaxHealthPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        new LuckPermsPermissionListener(this, LuckPermsProvider.get());
    }
}