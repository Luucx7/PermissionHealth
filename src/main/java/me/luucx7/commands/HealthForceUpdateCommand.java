package me.luucx7.commands;

import me.luucx7.core.MaxHealthHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealthForceUpdateCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 0)
        {
            if (!(sender instanceof Player)) { sender.sendMessage("Only players can run this command."); }
            else { MaxHealthHandler.applyHealthChange((Player) sender); }
        }
        else if (args[0].equals("*"))
        {
            for (Player player : Bukkit.getOnlinePlayers()) { MaxHealthHandler.applyHealthChange(player); }
            sender.sendMessage("Health update applied to all online players.");
        }
        else if (args.length == 1)
        {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target != null && target.isOnline())
            {
                MaxHealthHandler.applyHealthChange(target);
                sender.sendMessage("Health update applied to " + target.getName() + ".");
            }
            else
            {
                sender.sendMessage("Player '" + args[0] + "' is not online or does not exist.");
                return false;
            }
        }
        else
        {
            sender.sendMessage("Invalid command structure: /forceupdate ''/<player>/*");
            return false;
        }
        return true;
    }
}