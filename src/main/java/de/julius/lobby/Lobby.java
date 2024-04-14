package de.julius.lobby;

import de.julius.lobby.ServerSelector.ServerSelector;
import de.julius.lobby.commands.buildCommand;
import de.julius.lobby.commands.flyCommand;
import de.julius.lobby.listeners.*;
import de.julius.lobby.rank.rankListener;
import de.julius.lobby.spawn.setspawn;
import de.julius.lobby.spawn.spawn;
import de.julius.lobby.tablist.TablistManager;
import de.julius.lobby.util.LanguageUtils;
import de.julius.lobby.util.spawnUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.julius.lobby.ServerSelector.ServerSelector.plugin;

public final class Lobby extends JavaPlugin {

    public static List<Player> builders = new ArrayList<>();
    public static String discordLink = "discord.gg/SDawN8MYEV";
    private static TablistManager tablistManager;
    private static FileConfiguration config;
    private static Lobby instance;

    @Override
    public void onEnable() {

        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        // Register all commands
        loadCommands();

        // Register Listeners
        loadEvents();

        // Setup all configs
        loadConfigs();

        // Register messaging channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        tablistManager = new TablistManager();

        config = getConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadConfigs() {
        // Spawn config
        spawnUtils.setup();

        // Language configs
        LanguageUtils.setup("de-DE");
        LanguageUtils.save("de-DE");
        LanguageUtils.setup("en-US");
        LanguageUtils.save("en-US");

    }

    private void loadCommands() {
        getCommand("build").setExecutor(new buildCommand(this));
        getCommand("fly").setExecutor(new flyCommand());
        getCommand("setspawn").setExecutor(new setspawn());
        getCommand("spawn").setExecutor(new spawn());
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new JoinQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBurn(this), this);
        getServer().getPluginManager().registerEvents(new PlayerManager(this), this);
        getServer().getPluginManager().registerEvents(new ServerSelector(this), this);
        getServer().getPluginManager().registerEvents(new rankListener(this), this);
        getServer().getPluginManager().registerEvents(new chatListener(this), this);
    }

    public static TablistManager getTablistManager() {
        return tablistManager;
    }

    public static void teleportPlayerToServer(final Player player, final String server, Plugin plugin, InventoryClickEvent event){

        String message = Lobby.getConfigStrings("server-teleport-message").replaceAll("%server_name%", event.getCurrentItem().getItemMeta().getDisplayName());
        if (message == null) {
            message = ChatColor.RED + "Du wirst nun abgeschoben.";
            player.getInventory().getItemInMainHand().setType(Material.OAK_BOAT);
        }

        player.sendMessage(message);

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

//    public static String getPlayerRank(Player player) {
//
//        try {
//            String rank;
//            if (player.hasPermission("lobby.owner")) {
//                rank = "§4ᴏᴡɴᴇʀ §8| §7";
//            } else if (player.hasPermission("lobby.admin")) {
//                rank = "§cᴀᴅᴍɪɴɪꜱᴛʀᴀᴛᴏʀ §8| §7";
//            } else if (player.hasPermission("lobby.mod")) {
//                rank = "§2ᴍᴏᴅᴇʀᴀᴛᴏʀ §8| §7";
//            } else {
//                rank = "§fᴘʟᴀʏᴇʀ §8| §7";
//            }
//            return rank;
//        }catch (Exception e) {
//            Bukkit.getLogger().warning(" ");
//            Bukkit.getLogger().warning("An error occurred joining:");
//            Bukkit.getLogger().warning(" ");
//            Bukkit.getLogger().warning(">> " + e.getStackTrace());
//            Bukkit.getLogger().warning(" ");
//            Bukkit.getLogger().warning("You can report this on our Discord Server: " + Lobby.discordLink);
//            Bukkit.getLogger().warning(" ");
//            player.sendMessage("§cAn error occurred while joining.");
//            return "";
//        }
//
//    }

    public static String replacementForPluginsFont(String message) {

        return message.replaceAll("a", "ᴀ")
                .replaceAll("b", "ʙ")
                .replaceAll("c", "ᴄ")
                .replaceAll("d", "ᴅ")
                .replaceAll("e", "ᴇ")
                .replaceAll("f", "ꜰ")
                .replaceAll("g", "ɢ")
                .replaceAll("h", "ʜ")
                .replaceAll("i", "ɪ")
                .replaceAll("j", "ᴊ")
                .replaceAll("k", "ᴋ")
                .replaceAll("l", "ʟ")
                .replaceAll("m", "ᴍ")
                .replaceAll("n", "ɴ")
                .replaceAll("o", "ᴏ")
                .replaceAll("p", "ᴘ")
                .replaceAll("q", "ǫ")
                .replaceAll("r", "ʀ")
                .replaceAll("s", "ѕ")
                .replaceAll("t", "ᴛ")
                .replaceAll("u", "ᴜ")
                .replaceAll("v", "ᴠ")
                .replaceAll("w", "ᴡ")
                .replaceAll("x", "х")
                .replaceAll("y", "ʏ")
                .replaceAll("z", "ᴢ");

    }

    public static String getConfigStrings(String string) {

        try {
            return LanguageUtils.get(plugin.getConfig().getString("used-language")).getString(string);
        }catch (Exception e) {
            return "";
        }


    }


}
