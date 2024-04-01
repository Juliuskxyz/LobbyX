package de.julius.lobby.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtils {
    public static Scoreboard getBaseScoreboard(Player player) {

        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        String rank;
        if (player.hasPermission("lobby.owner")) {
            rank = "§9Owner";
        } else if (player.hasPermission("lobby.admin")) {
            rank = "§cAdministrator";
        } else if (player.hasPermission("lobby.mod")) {
            rank = "§2Moderator";
        } else {
            rank = "§fPlayer";
        }

        Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = s.registerNewObjective("main", "main", "    §9§lɴᴇʙᴜʟᴀʀ §f§lɴᴇᴛᴡᴏʀᴋ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("    §8◆§m                                   §r§8◆  ").setScore(7);
        objective.getScore("      §lᴘʟᴀʏᴇʀ").setScore(6);
        objective.getScore("       §8 ▪ §7ʀᴀɴᴋ: " + rank).setScore(5);
        objective.getScore("       §8 ▪ §7ᴘɪɴɢ: " + "§f" + player.getPing()).setScore(4);
        objective.getScore("      §lꜱᴇʀᴠᴇʀ").setScore(3);
        objective.getScore("       §8 ▪ §7ᴏɴʟɪɴᴇ: " + "§f" + onlinePlayers).setScore(2);
        objective.getScore("    §8◆§m                                   §r§8◆ ").setScore(1);
        objective.getScore("        §7ᴘʟᴀʏ.ɴᴇʙᴜʟᴀʀɴᴏᴅᴇꜱ.ᴄᴏᴍ").setScore(0);

        //    NEBULAR NETWORK       Title
        //-----------------------   7
        //PLAYER                    6
        // • Rank: Owner            5
        // • Ping: 10ms         /   4
        //SERVER                    3
        // • Online: 1          /   2
        //-----------------------   1
        //play.nebularnodes.com     0

        return s;
    }

}
