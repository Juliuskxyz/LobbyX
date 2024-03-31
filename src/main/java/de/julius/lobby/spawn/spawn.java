package de.julius.lobby.spawn;

import de.julius.lobby.Lobby;
import de.julius.lobby.util.spawnUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class spawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(command.getName().equalsIgnoreCase("spawn"))) {
            return true;
        }

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Lobby.getConfigString("send-by-console"));
            return true;
        }

        Player player = (Player) commandSender;

        if (!(player.hasPermission("lobby.spawn"))) {
            player.sendMessage(Lobby.getConfigString("no-permission"));
            return true;
        }

        if (spawnUtils.get().getLocation("spawn") == null) {
            player.sendMessage("§cSorry, but there is currently no spawn set.");
            return true;
        }

        player.teleport(spawnUtils.get().getLocation("spawn"));
        player.sendMessage(Lobby.getConfigString("spawn-teleport"));

        return true;
    }
}
