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
            String sendByConsole = this.plugin.getConfig().getString("send-by-console");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', sendByConsole));
            return true;
        }

        Player player = (Player) commandSender;

        if (!(player.hasPermission("lobby.fly"))) {
            String noPermission = this.plugin.getConfig().getString("no-permission");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
            return true;
        }

        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            String disableFly = this.plugin.getConfig().getString("flying-disabled");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', disableFly));
            return true;
        }else {
            player.setAllowFlight(true);
            String enableFly = this.plugin.getConfig().getString("flying-enabled");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', enableFly));
            return true;
        }
    }
}

