package de.julius.lobby.tablist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager {

    public void setTablist(Player player) {
        player.setPlayerListHeaderFooter(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "           " +
                ChatColor.DARK_GRAY + "[ " + ChatColor.BLUE + "Nebular " + ChatColor.DARK_GRAY + "]" +
                ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "            " + ChatColor.RESET +
                "\n" + "", "\n" + "" + "\n" + "play.nebularnodes.com");
    }

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        Team players = scoreboard.getTeam("010players");

        if (players == null) {
            players = scoreboard.registerNewTeam("010players");
        }

        Team operators = scoreboard.getTeam("001owners");

        if (operators == null) {
            operators = scoreboard.registerNewTeam("001owners");

        }

        players.setPrefix(ChatColor.GRAY + "Player" + ChatColor.DARK_GRAY + "| ");
        players.setColor(ChatColor.GRAY);

        operators.setPrefix(ChatColor.DARK_RED + "Owner " + ChatColor.DARK_GRAY + "| ");
        operators.setColor(ChatColor.GRAY);

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.isOp()) {
                operators.addEntry(target.getName());
                continue;
            }

            players.addEntry(target.getName());

        }

    }
}