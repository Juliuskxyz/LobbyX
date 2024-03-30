package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockDamageListener implements Listener {

    public final Lobby plugin;

    public BlockDamageListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockDamage(BlockDamageEvent e) {
        if (plugin.getConfig().getBoolean("block-damage"))
            e.setCancelled(true);
    }

}
