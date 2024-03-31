package de.julius.lobby.commands;

import de.julius.lobby.Lobby;
import org.bukkit.Bukkit;
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

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("build")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (player.hasPermission("lobby.build")) {
                    if (args.length == 1) {
                        try {
                            Player target = Bukkit.getPlayer(args[0]);

                            if (!Lobby.builders.contains(target)) {
                                Lobby.builders.add(target);
                                String buildOnMessage = this.plugin.getConfig().getString("build-on");
                                player.sendMessage(Lobby.getConfigString("build-target-on").replaceAll("%player%", target.getName()));
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', buildOnMessage));
                                target.setGameMode(GameMode.CREATIVE);
                                target.getInventory().clear();
                            } else {
                                Lobby.builders.remove(target);
                                String buildOffMessage = this.plugin.getConfig().getString("build-off");
                                player.sendMessage(Lobby.getConfigString("build-target-off").replaceAll("%player%", target.getName()));
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', buildOffMessage));
                                target.setGameMode(GameMode.ADVENTURE);
                                target.getInventory().setItem(4, getSelector());
                            }
                        }catch (Exception e) {
                            player.sendMessage("§c" + args[0] + " was not found.");
                            return true;
                        }
                    }else {
                        if (!Lobby.builders.contains(player)) {
                            Lobby.builders.add(player);
                            String buildOnMessage = this.plugin.getConfig().getString("build-on");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', buildOnMessage));
                            player.setGameMode(GameMode.CREATIVE);
                            player.getInventory().clear();
                        } else {
                            Lobby.builders.remove(player);
                            String buildOffMessage = this.plugin.getConfig().getString("build-off");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', buildOffMessage));
                            player.setGameMode(GameMode.ADVENTURE);
                            player.getInventory().setItem(4, getSelector());
                        }
                    }
                } else {
                    String noPermission = this.plugin.getConfig().getString("no-permission");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                }
            } else {
                String sendByConsole = this.plugin.getConfig().getString("send-by-console");
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', sendByConsole));
            }
            return true;
        }
        return false;
    }
}
