package de.julius.lobby.spawn;

import de.julius.lobby.Lobby;
import de.julius.lobby.util.spawnUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setspawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(command.getName().equalsIgnoreCase("setspawn"))) {
            return true;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Lobby.getConfigString("send-by-console"));
            return true;
        }

        Player player = (Player) commandSender;

        if (!(player.hasPermission("lobby.setspawn"))) {
            player.sendMessage(Lobby.getConfigString("no-permission"));
            return true;
        }

        Location currentPlayerLocation = player.getLocation();

        try {
            spawnUtils.get().set("spawn", currentPlayerLocation);
            spawnUtils.save();
            player.sendMessage(Lobby.getConfigString("spawn-set"));
        }catch (Exception e) {
            Bukkit.getLogger().warning(" ");
            Bukkit.getLogger().warning("An error occurred while setting the Spawn:");
            Bukkit.getLogger().warning(" ");
            Bukkit.getLogger().warning(">> " + e.getStackTrace());
            Bukkit.getLogger().warning(" ");
            Bukkit.getLogger().warning("You can report this on our Discord Server: " + Lobby.discordLink);
            Bukkit.getLogger().warning(" ");
            player.sendMessage("§cAn error occurred while setting the Spawn.");
        }

        return true;
    }
}
