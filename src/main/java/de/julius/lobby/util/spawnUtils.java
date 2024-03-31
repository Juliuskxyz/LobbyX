package de.julius.lobby.util;

import de.julius.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class spawnUtils {

    private static Lobby plugin;
    public spawnUtils(Lobby plugin) {
        this.plugin = plugin;
    }
    private static File file;
    private static FileConfiguration customFile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Lobby").getDataFolder(), "data/spawn.yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                Bukkit.getServer().getPluginManager().getPlugin("Lobby").saveResource("data/spawn.yml", true);
            } catch (IOException var1) {
                Bukkit.getLogger().warning("The file spawn.yml could not be created!");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException var1) {
            Bukkit.getLogger().warning("The file spawn.yml could not be saved!");
        }
    }

}
