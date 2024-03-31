package de.julius.lobby.commands;

import com.sun.jdi.event.ExceptionEvent;
import de.julius.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class flyCommand implements CommandExecutor {

    Integer flySpeed;
    Player target;

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

        try {

            if (args.length == 1) {
                flySpeed = Integer.valueOf(args[0]);
            }else if (args.length == 2) {
                flySpeed = Integer.valueOf(args[0]);
                target = Bukkit.getPlayer(args[1]);
            }else {
                flySpeed = 1;
                target = player;
            }

            togglePlayerFly(player, flySpeed, target);
        }catch (Exception e) {
            System.out.println(" ");
            System.out.println("An error occurred while change a players fly state:");
            System.out.println(" ");
            System.out.println(">> " + e.getStackTrace());
            System.out.println(" ");
            System.out.println("You can report this on our Discord Server: " + Lobby.discordLink);
            System.out.println(" ");
            player.sendMessage("§cAn error occurred while change a players fly state.");
        }

        return true;
    }

    private static void togglePlayerFly(Player player, Integer args, Player target) {

        if (target.getAllowFlight() && args == 1) {
            player.setFlySpeed(0.1f);
            target.setAllowFlight(false);

            if (target == player) {
                player.sendMessage(Lobby.getConfigString("flying-disabled"));
            }else {
                player.sendMessage(Lobby.getConfigString("flying-target-disabled").replaceAll("%player%", target.getName()));
                target.sendMessage(Lobby.getConfigString("flying-disabled"));
            }
        }else {
            try {
                player.setAllowFlight(true);
                if (args > 10 || args < 1) {
                    player.sendMessage("§cPlease select a Fly Speed Value between 1-10");
                }else {
                    if (target == player) {
                        player.setFlySpeed(args * 0.1f);
                        player.sendMessage(Lobby.getConfigString("flying-enabled"));
                    }else {
                        player.sendMessage(Lobby.getConfigString("flying-target-enabled").replaceAll("%player%", target.getName()));
                        target.sendMessage(Lobby.getConfigString("flying-enabled"));
                        target.setFlySpeed(args * 0.1f);
                    }
                }

            }catch (Exception e) {
                player.sendMessage("§cWrong Use! please use /fly [<speed>] [<playername>]");
            }
        }
    }

}

