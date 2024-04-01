package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockDamageListener implements Listener {

    public final Lobby plugin;

    public BlockDamageListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent e) {
        if (plugin.getConfig().getBoolean("block-explode"))
            e.blockList().clear();
    }

}
