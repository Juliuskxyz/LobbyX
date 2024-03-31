package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinQuitListener implements Listener {

    private final Lobby plugin;

    public JoinQuitListener(Lobby plugin) {
        this.plugin = plugin;
        }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        player.setGameMode(GameMode.ADVENTURE);

        e.setJoinMessage("");

        Lobby.getTablistManager().setPlayerTeams(player);
        Lobby.getTablistManager().setAllPlayerTeams();
        Lobby.getTablistManager().setTablist(player);

        String joinMessage = this.plugin.getConfig().getString("join-message");
        if (joinMessage != null) {
            joinMessage = joinMessage.replace("%player%", e.getPlayer().getDisplayName());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
        }
    }
}
