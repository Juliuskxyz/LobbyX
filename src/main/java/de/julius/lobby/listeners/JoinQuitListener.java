package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import de.julius.lobby.tablist.TablistManager;
import de.julius.lobby.util.ScoreboardUtils;
import de.julius.lobby.util.spawnUtils;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    private static Lobby plugin;

    public JoinQuitListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        //Player inventory clear
        event.getPlayer().getInventory().clear();

        //Player gamemode
        player.setGameMode(GameMode.ADVENTURE);

        // Scoreboard manager
        event.getPlayer().setScoreboard(ScoreboardUtils.getBaseScoreboard(event.getPlayer()));

        //Tablist setup
//        Lobby.getTablistManager().setPlayerTeams(player);
//        Lobby.getTablistManager().setAllPlayerTeams();
        Lobby.getTablistManager().setTablist(player);

        String prefix = LuckPermsProvider.get().getUserManager().getUser(event.getPlayer().getName()).getCachedData().getMetaData().getPrefix();

        try {
            //Personal join message
            event.getPlayer().sendMessage(Lobby.getConfigStrings("join-message-personal").replaceAll("%prefix%", prefix).replaceAll("%player%", event.getPlayer().getName()));
        }catch (Exception e) {}

        try {
            //Global join message
            event.setJoinMessage(Lobby.getConfigStrings("join-message-global").replaceAll("%prefix%", prefix).replaceAll("%player%", event.getPlayer().getName()));
        }catch (Exception e) {}


        //Spawn Teleport
        if (spawnUtils.get().getLocation("spawn") != null) {
            player.teleport(spawnUtils.get().getLocation("spawn"));
        }

        // Changes the Player names font in the Tablist
        TablistManager.changePlayerNameFont(event.getPlayer());

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        event.setQuitMessage(Lobby.getConfigStrings("quit-message").replaceAll("%player%", player.getName()));

    }

}
