package de.julius.lobby;

import de.julius.lobby.ServerSelector.ServerSelector;
import de.julius.lobby.commands.buildCommand;
import de.julius.lobby.commands.flyCommand;
import de.julius.lobby.listeners.*;
import de.julius.lobby.tablist.TablistManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Lobby extends JavaPlugin {

    public static List<Player> builders = new ArrayList<>();

    public static String prefix = ChatColor.GREEN + "Nebular" + ChatColor.GRAY + "SMP" + ChatColor.DARK_GREEN + " > ";

    private static TablistManager tablistManager;

    private static FileConfiguration config;

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
        getServer().getPluginManager().registerEvents(new ServerSelector(this), this);

        //register Commands
        this.getCommand("build").setExecutor(new buildCommand(this));
        this.getCommand("fly").setExecutor(new flyCommand(this));

        // Register messaging channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        tablistManager = new TablistManager();

        config = getConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TablistManager getTablistManager() {
        return tablistManager;
    }

    public static void teleportPlayerToServer(final Player player, final String server, Plugin plugin){
        final String message = plugin.getConfig().getString("server-teleport-message");
        if (message != null) {
            player.sendMessage(ChatColor.RED + "Du wirst nun abgeschoben.");
        }

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos)
        ){

            dos.writeUTF("Connect");
            dos.writeUTF(server);
            player.sendPluginMessage(plugin, "BungeeCord", baos.toByteArray());
        } catch (final IOException e){
            e.printStackTrace();
        }
    }

    public static String getConfigString(String string) {
        String sendByConsole = config.getString(string);
        return ChatColor.translateAlternateColorCodes('&', sendByConsole);
    }
}
