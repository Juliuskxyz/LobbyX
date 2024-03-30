package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

public class BlockBurn implements Listener {

    private final Lobby plugin;


    public BlockBurn(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        if (plugin.getConfig().getBoolean("Block-burn")) {
            e.setCancelled(true);
        }
    }
}
