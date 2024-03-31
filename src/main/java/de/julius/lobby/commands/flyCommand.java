package de.julius.lobby.commands;

import de.julius.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class flyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(command.getName().equalsIgnoreCase("fly"))) {
            return true;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Lobby.getConfigString("send-by-console"));
            return true;
        }

        Player player = (Player) commandSender;

        if (!(player.hasPermission("lobby.fly"))) {
            player.sendMessage(Lobby.getConfigString("no-permission"));
            return true;
        }

        if (player.getAllowFlight() && args.length == 0) {
            player.setFlySpeed(0.1f);
            player.setAllowFlight(false);
            player.sendMessage(Lobby.getConfigString("flying-disabled"));
            return true;
        }else {
            player.setAllowFlight(true);
            if (args.length == 1) {
                try {
                    if (Integer.parseInt(args[0]) > 10 || Integer.parseInt(args[0]) < 1) {
                        player.sendMessage("§cPlease select a Fly Speed Value between 1-10");
                        return true;
                    }
                    player.setFlySpeed(Float.parseFloat(args[0]) * 0.1f);
                }catch (Exception e) {
                    player.sendMessage("§cWrong Use! please use /fly <speed>");
                    return true;
                }
            }
            player.sendMessage(Lobby.getConfigString("flying-enabled"));
            return true;
        }
    }
}

