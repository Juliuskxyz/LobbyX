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

    private final Lobby plugin;
    public flyCommand(Lobby plugin) {
        this.plugin = plugin;
    }

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

        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.sendMessage(Lobby.getConfigString("flying-disabled"));
            return true;
        }else {
            player.setAllowFlight(true);
            player.sendMessage(Lobby.getConfigString("flying-enabled"));
            return true;
        }
    }
}

