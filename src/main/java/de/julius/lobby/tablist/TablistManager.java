package de.julius.lobby.tablist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager {

    public void setTablist(Player player) {
        player.setPlayerListHeaderFooter(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "           " +
                ChatColor.DARK_GRAY + "[ " + ChatColor.BLUE + "ɴᴇʙᴜʟᴀʀ " + ChatColor.DARK_GRAY + "]" +
                ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "            " + ChatColor.RESET +
                "\n" + "", "\n" + "" + ChatColor.GRAY + "ᴘʟᴀʏ.ɴᴇʙᴜʟᴀʀɴᴏᴅᴇꜱ.ᴄᴏᴍ" + "\n" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH
                + "                                      ");
    }

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        Team players = scoreboard.getTeam("1000players");
        if (players == null) {
            players = scoreboard.registerNewTeam("1000players");
        }
        players.setPrefix("§fᴘʟᴀʏᴇʀ §8| §7");
        players.setColor(ChatColor.GRAY);


        Team admins = scoreboard.getTeam("0010admin");
        if (admins == null) {
            admins = scoreboard.registerNewTeam("0010admin");
        }
        admins.setPrefix("§cᴀᴅᴍɪɴɪꜱᴛʀᴀᴛᴏʀ §8| §7");
        admins.setColor(ChatColor.GRAY);

        Team moderators = scoreboard.getTeam("0100moderator");
        if (moderators == null) {
            moderators = scoreboard.registerNewTeam("0100moderator");
        }
        moderators.setPrefix("§2ᴍᴏᴅᴇʀᴀᴛᴏʀ §8| §7");
        moderators.setColor(ChatColor.GRAY);

        Team operators = scoreboard.getTeam("001owners");
        if (operators == null) {
            operators = scoreboard.registerNewTeam("001owners");
        }
        operators.setPrefix("§9ᴏᴡɴᴇʀ §8| §7");
        operators.setColor(ChatColor.GRAY);

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("lobby.owner")) {
                operators.addEntry(target.getName());
            } else if (player.hasPermission("lobby.admin")) {
                admins.addEntry(target.getName());
            } else if (player.hasPermission("lobby.mod")) {
                moderators.addEntry(target.getName());
            } else {
                players.addEntry(target.getName());
            }
        }


    }

}