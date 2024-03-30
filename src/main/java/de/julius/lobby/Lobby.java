package de.julius.lobby;

import de.julius.lobby.commands.buildCommand;
import de.julius.lobby.commands.flyCommand;
import de.julius.lobby.listeners.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Lobby extends JavaPlugin {

    public static List<Player> builders = new ArrayList<>();

    public static String prefix = ChatColor.GREEN + "Nebular" + ChatColor.GRAY + "SMP" + ChatColor.DARK_GREEN + " > ";
    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        // Register Listeners
        getServer().getPluginManager().registerEvents(new JoinQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBurn(this), this);
        getServer().getPluginManager().registerEvents(new PlayerManager(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        //register Commands
        this.getCommand("build").setExecutor(new buildCommand(this));
        this.getCommand("fly").setExecutor(new flyCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
