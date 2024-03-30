package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    private final Lobby plugin;


    public WeatherListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (plugin.getConfig().getBoolean("Disable-rain")) {
            e.setCancelled(true);
        }
    }
}
