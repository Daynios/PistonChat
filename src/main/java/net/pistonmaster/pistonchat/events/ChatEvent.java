package net.pistonmaster.pistonchat.events;

import lombok.RequiredArgsConstructor;
import net.pistonmaster.pistonchat.PistonChat;
import net.pistonmaster.pistonchat.api.PistonChatEvent;
import net.pistonmaster.pistonchat.api.PistonChatReceiveEvent;
import net.pistonmaster.pistonchat.utils.CommonTool;
import net.pistonmaster.pistonchat.utils.LanguageTool;
import net.pistonmaster.pistonchat.utils.PlatformUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class ChatEvent implements Listener {
    private final PistonChat plugin;

    // Mute plugins should have a lower priority to work!
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player chatter = event.getPlayer();
        PistonChatEvent pistonChatEvent = new PistonChatEvent(chatter, event.getMessage(), event.isAsynchronous());

        event.getRecipients().clear();

        Bukkit.getPluginManager().callEvent(pistonChatEvent);

        event.setCancelled(pistonChatEvent.isCancelled());

        if (pistonChatEvent.isCancelled()) {
            return;
        }

        if (plugin.getTempDataTool().isChatEnabled(chatter)) {
            for (Player receiver : PlatformUtils.getOnlinePlayers()) {
                if (!plugin.getIgnoreTool().isIgnored(chatter, receiver) && plugin.getTempDataTool().isChatEnabled(receiver)) {
                    PistonChatReceiveEvent perPlayerEvent = new PistonChatReceiveEvent(chatter, receiver, pistonChatEvent.getMessage(), event.isAsynchronous());

                    Bukkit.getPluginManager().callEvent(perPlayerEvent);

                    if (perPlayerEvent.isCancelled())
                        continue;

                    CommonTool.sendChatMessage(chatter, perPlayerEvent.getMessage(), receiver);
                }
            }
        } else {
            chatter.sendMessage(LanguageTool.getMessage("chatisoff"));
            event.setCancelled(true);
        }
    }
}
