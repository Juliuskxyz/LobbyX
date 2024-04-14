package de.julius.lobby.rank;

import de.julius.lobby.Lobby;
import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;

public class rankListener implements Listener {

    private static Lobby plugin;
    public rankListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void playerJoin(PlayerJoinEvent event) {

        Iterable<Group> groups = LuckPermsProvider.get().getGroupManager().getLoadedGroups();

        for (Group group : groups) {
            if (PlaceholderAPI.setPlaceholders(event.getPlayer(), "%luckperms_groups%").contains(group.getName())) {
                Team team;
                if (group.getWeight() == null) {
                    team = event.getPlayer().getScoreboard().getTeam(group.getName());
                }else {
                    team = event.getPlayer().getScoreboard().getTeam(group.getWeight().getAsInt() + group.getName());
                }
                if (event.getPlayer().getScoreboard().getTeam(group.getWeight().getAsInt() + group.getName()) == null) {
                    team = event.getPlayer().getScoreboard().registerNewTeam(group.getWeight().getAsInt() + group.getName());
                }
                team.setPrefix(playerRank(event.getPlayer()));
                team.addEntry(event.getPlayer().getName());
            }
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public static void setEventMessage(PlayerJoinEvent event) {

        String prefix = LuckPermsProvider.get().getUserManager().getUser(event.getPlayer().getName()).getCachedData().getMetaData().getPrefix();

        if (Lobby.getConfigStrings("join-message") != null) {
            event.setJoinMessage(Lobby.getConfigStrings("join-message").replaceAll("%prefix%", prefix).replaceAll("%player%", event.getPlayer().getName()));
        }else {
            event.setJoinMessage("");
        }

    }

    @EventHandler (priority = EventPriority.LOW)
    public static void setEventMessageQuit(PlayerQuitEvent event) {

        String prefix = LuckPermsProvider.get().getUserManager().getUser(event.getPlayer().getName()).getCachedData().getMetaData().getPrefix();

        event.setQuitMessage(Lobby.getConfigStrings("quit-message").replaceAll("%prefix%", prefix).replaceAll("%player%", event.getPlayer().getName()));
    }

    public static String playerRank(Player player) {
        return LuckPermsProvider.get().getUserManager().getUser(player.getName()).getCachedData().getMetaData().getPrefix() + " ";
    }

}
