package de.julius.lobby.commands;

import de.julius.lobby.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.julius.lobby.ServerSelector.ServerSelector.getSelector;

public class buildCommand implements CommandExecutor {

    private final Lobby plugin;

    public buildCommand(Lobby plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (command.getName().equalsIgnoreCase("build")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (player.hasPermission("lobby.build")) {
                    if (!Lobby.builders.contains(player)) {
                        Lobby.builders.add(player);
                        String buildOnMessage = Lobby.getConfigStrings("command.build.enabled");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', buildOnMessage));
                        player.setGameMode(GameMode.CREATIVE);
                        player.getInventory().clear();
                    } else {
                        Lobby.builders.remove(player);
                        String buildOffMessage = Lobby.getConfigStrings("command.build.disabled");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', buildOffMessage));
                        player.setGameMode(GameMode.ADVENTURE);
                        player.getInventory().setItem(4, getSelector());
                    }
                } else {
                    String noPermission = Lobby.getConfigStrings("no-permission");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                }
            } else {
                String sendByConsole = Lobby.getConfigStrings("send-by-console");
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', sendByConsole));
            }
            return true;
        }
        return false;
    }

}
