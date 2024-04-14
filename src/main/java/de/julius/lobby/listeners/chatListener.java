package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatListener implements Listener {

    private static Lobby plugin;
    public chatListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent event) {

        String prefix = LuckPermsProvider.get().getUserManager().getUser(event.getPlayer().getName()).getCachedData().getMetaData().getPrefix();

        String message = plugin.getConfig().getString("ChatFormat").replaceAll("%prefix%", prefix).replaceAll("%player%", event.getPlayer().getName());

        if (message.contains("%colormessage%")) {
            message = message.replaceAll("%colormessage%", ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        }else {
            message = message.replaceAll("%message%", event.getMessage());
        }

        message = Lobby.replacementForPluginsFont(message.toLowerCase());

        event.setFormat(message);
}

}
