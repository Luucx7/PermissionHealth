package me.luucx7.luckperms;

import me.luucx7.MaxHealthPlugin;
import me.luucx7.core.MaxHealthHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeClearEvent;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.model.PermissionHolder;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class LuckPermsPermissionListener {

    private final MaxHealthPlugin plugin;

    public LuckPermsPermissionListener(MaxHealthPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;

        EventBus eventBus = luckPerms.getEventBus();

        eventBus.subscribe(this.plugin, NodeAddEvent.class, this::nodeAddEvent);
        eventBus.subscribe(this.plugin, NodeRemoveEvent.class, this::nodeRemoveEvent);
        eventBus.subscribe(this.plugin, NodeClearEvent.class, this::nodeRemoveEvent);
    }

    private void nodeAddEvent(NodeMutateEvent event) {
        List<Node> nodes = event.getDataAfter().stream()
                .filter(node -> node.getType() == NodeType.PERMISSION)
                .filter(node -> node.getKey().startsWith(MaxHealthPlugin.PERMISSION))
                .toList();

        if (nodes.size() == 0) return;

        if (event.getTarget().getIdentifier().getType().equals(PermissionHolder.Identifier.USER_TYPE)) {
            UUID userID = UUID.fromString(event.getTarget().getIdentifier().getName());
            Player player = Bukkit.getPlayer(userID);

            if (player == null) throw new RuntimeException("Player with UUID " + userID.toString() + " was not found.");

            MaxHealthHandler.applyHealthChange(player);
        } else {
            String groupName = event.getTarget().getIdentifier().getName();

            // Lazy solution:
            Bukkit.getOnlinePlayers().forEach((MaxHealthHandler::applyHealthChange));

            // Good solution:
            // TODO: filter every player that was affected by this change
        }
    }

    private void nodeRemoveEvent(NodeMutateEvent event) {
        List<Node> nodes = event.getDataBefore().stream()
                .filter(node -> node.getType() == NodeType.PERMISSION)
                .filter(node -> node.getKey().startsWith(MaxHealthPlugin.PERMISSION))
                .toList();

        if (nodes.size() == 0) return;

        if (event.getTarget().getIdentifier().getType().equals(PermissionHolder.Identifier.USER_TYPE)) {
            UUID userID = UUID.fromString(event.getTarget().getIdentifier().getName());
            Player player = Bukkit.getPlayer(userID);

            if (player == null) throw new RuntimeException("Player with UUID " + userID.toString() + " was not found.");

            MaxHealthHandler.applyHealthChange(player);
        } else {
            String groupName = event.getTarget().getIdentifier().getName();

            // Lazy solution:
            Bukkit.getOnlinePlayers().forEach((MaxHealthHandler::applyHealthChange));

            // Good solution:
            // TODO: filter every player that was affected by this change
        }
    }
}
