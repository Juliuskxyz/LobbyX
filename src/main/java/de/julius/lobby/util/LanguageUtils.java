package de.julius.lobby.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LanguageUtils {

    private static File file;
    private static FileConfiguration customFile;

    public static void setup(String language) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Lobby").getDataFolder(), "lang/" + language + ".yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                Bukkit.getServer().getPluginManager().getPlugin("Lobby").saveResource("lang/" + language + ".yml", true);
            } catch (IOException var1) {
                Bukkit.getLogger().warning("The file " + language + ".yml could not be created!");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(String language) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Lobby").getDataFolder(), "lang/" + language + ".yml");
        customFile = YamlConfiguration.loadConfiguration(file);
        return customFile;
    }

    public static void save(String language) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Lobby").getDataFolder(), "lang/" + language + ".yml");
        customFile = YamlConfiguration.loadConfiguration(file);
        try {
            customFile.save(file);
        } catch (IOException var1) {
            Bukkit.getLogger().warning("The file " + language + ".yml could not be created!");
        }
    }

}
