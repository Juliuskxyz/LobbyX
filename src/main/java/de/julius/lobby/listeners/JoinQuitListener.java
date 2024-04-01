package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import de.julius.lobby.util.ScoreboardUtils;
import de.julius.lobby.util.spawnUtils;
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

        e.getPlayer().getInventory().clear();

        player.setGameMode(GameMode.ADVENTURE);

        e.setJoinMessage("");

        e.getPlayer().setScoreboard(ScoreboardUtils.getBaseScoreboard(e.getPlayer()));

        Lobby.getTablistManager().setPlayerTeams(player);
        Lobby.getTablistManager().setAllPlayerTeams();
        Lobby.getTablistManager().setTablist(player);

        String joinMessage = this.plugin.getConfig().getString("join-message");
        if (joinMessage != null) {
            joinMessage = joinMessage.replace("%player%", e.getPlayer().getDisplayName());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
        }

        if (spawnUtils.get().getLocation("spawn") != null) {
            player.teleport(spawnUtils.get().getLocation("spawn"));
        }
    }
}
