package me.alexprogrammerde.pistonchat.commands;

import me.alexprogrammerde.pistonchat.utils.ConfigTool;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class IgnoreListCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            List<String> list = ConfigTool.getIgnoredPlayers(player);

            if (list.isEmpty()) {
                player.sendMessage("You have no players ignored!");
            } else {
                player.sendMessage("All ignored players: " + String.join(", ", list));
            }
        } else {
            sender.sendMessage("You need to be a player to do that!");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}