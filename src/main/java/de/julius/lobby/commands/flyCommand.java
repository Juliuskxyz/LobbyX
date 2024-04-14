package de.julius.lobby.commands;

import de.julius.lobby.Lobby;
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
            commandSender.sendMessage(Lobby.getConfigStrings("send-by-console"));
            return true;
        }

        Player player = (Player) commandSender;

        if (!(player.hasPermission("lobby.fly"))) {
            player.sendMessage(Lobby.getConfigStrings("no-permission"));
            return true;
        }

        if (player.getAllowFlight() && args.length == 0) {
            player.setFlySpeed(0.1f);
            player.setAllowFlight(false);
            player.sendMessage(Lobby.getConfigStrings("command.fly.disabled"));
            return true;
        }else {

            if (args.length == 1) {
                try {
                    if (Integer.parseInt(args[0]) > 10 || Integer.parseInt(args[0]) < 1) {
                        player.sendMessage(Lobby.getConfigStrings("command.fly.speed"));
                        return true;
                    }
                    player.setFlySpeed(Float.parseFloat(args[0]) * 0.1f);
                }catch (Exception e) {
                    player.sendMessage(Lobby.getConfigStrings("command.fly.wrong-use"));
                    return true;
                }
            }
            if (!(player.getAllowFlight())) {
                player.sendMessage(Lobby.getConfigStrings("command.fly.enabled"));
            }
            player.setAllowFlight(true);
            return true;
        }
    }
}

