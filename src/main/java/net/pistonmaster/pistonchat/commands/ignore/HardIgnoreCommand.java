package net.pistonmaster.pistonchat.commands.ignore;

import lombok.RequiredArgsConstructor;
import net.pistonmaster.pistonchat.PistonChat;
import net.pistonmaster.pistonchat.utils.HardIgnoreTool;
import net.pistonmaster.pistonchat.utils.LanguageTool;
import net.pistonmaster.pistonchat.utils.PlatformUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HardIgnoreCommand implements CommandExecutor, TabExecutor {
    private final PistonChat plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                Optional<Player> ignored = PlatformUtils.getPlayer(args[0]);

                if (ignored.isPresent()) {
                    HardIgnoreTool.HardReturn type = plugin.getHardIgnoreTool().hardIgnorePlayer(player, ignored.get());

                    if (type == HardIgnoreTool.HardReturn.IGNORE) {
                        player.sendMessage(plugin.getHardIgnoreTool().getPreparedString("ignorehard", ignored.get()));
                    } else if (type == HardIgnoreTool.HardReturn.UN_IGNORE) {
                        player.sendMessage(plugin.getHardIgnoreTool().getPreparedString("unignorehard", ignored.get()));
                    }
                } else {
                    player.sendMessage(LanguageTool.getMessage("notonline"));
                }
            } else {
                return false;
            }
        } else {
            sender.sendMessage(LanguageTool.getMessage("playeronly"));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return null;
        } else {
            return Collections.emptyList();
        }
    }
}
