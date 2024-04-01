package de.julius.lobby.util;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtils {
    public static Scoreboard getBaseScoreboard(Player player) {

        Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = s.registerNewObjective("main", "main", "§aNEBULAR §fNETWORK");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("§a").setScore(8);
        objective.getScore("§8§m                      ").setScore(7);
        objective.getScore("§fPLAYER").setScore(6);
        objective.getScore("§8• §7Rank: " + (player.isOp() ? "§cOwner" : "§7Spieler")).setScore(5);
        objective.getScore("§8• §7Ping: " + player.getPing()).setScore(4);
        objective.getScore("§fServer").setScore(3);
        objective.getScore("§8• §7Online: ").setScore(2);
        objective.getScore("§8§m                      ").setScore(1);
        objective.getScore("§7play.nebularnodes.com").setScore(0);

        //    NEBULAR NETWORK       8
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
