package de.julius.lobby.listeners;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockDestroy implements Listener {

    @EventHandler
    public void onBlockDestroy(BlockDestroyEvent e) {
        e.setCancelled(true);
    }

}
