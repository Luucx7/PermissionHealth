package me.luucx7.core;

import me.luucx7.MaxHealthPlugin;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MaxHealthHandler {

    private static int getPlayerMaxHP(Player player) {
        String permPrefix = MaxHealthPlugin.PERMISSION;

        int defaultHP;
        if (!(MaxHealthPlugin.getInstance().getConfig().isInt("default_hp"))) defaultHP = 20;
        else defaultHP = MaxHealthPlugin.getInstance().getConfig().getInt("default_hp");

        List<PermissionAttachmentInfo> perms = player.getEffectivePermissions().stream().filter(PermissionAttachmentInfo::getValue).filter((x) -> x.getPermission().startsWith(permPrefix + ".")).collect(Collectors.toList());
        if (perms.size() == 0) return defaultHP;

        AtomicInteger maxVal = new AtomicInteger(0);
        perms.forEach((perm) -> {
            String permPart = perm.getPermission().replace(permPrefix + ".", "");
            int hp = Integer.parseInt(permPart);
            if (hp > maxVal.get()) maxVal.set(hp);
        });

        if (maxVal.intValue() > 0) return maxVal.intValue();

        return defaultHP;
    }

    public static void applyHealthChange(final Player player) {
        final double maxHpBefore = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        // 1 tick after because some things are hella buggy sometimes
        Bukkit.getScheduler().runTaskLater(MaxHealthPlugin.getInstance(), () -> {
            final int maxHP = getPlayerMaxHP(player);
            final int foodLvl = player.getFoodLevel();

            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHP);
            player.setFoodLevel(Math.max(foodLvl - 1, 0)); // On version before 1.19 hunger is somehow related to how health is updated, and this fixes the issue

            // another tick after because lol minecraft
            Bukkit.getScheduler().runTaskLater(MaxHealthPlugin.getInstance(), () -> {
                if (player.getHealth() >= maxHpBefore) {
                    player.setHealth(maxHP);
                }

                player.setFoodLevel(foodLvl); // leave hunger as it was before the manipulation
            }, 1);
        }, 1);
    }
}
