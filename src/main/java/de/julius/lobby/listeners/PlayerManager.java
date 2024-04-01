package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import de.julius.lobby.util.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerManager implements Listener {

    public final Lobby plugin;

    public PlayerManager(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHandSwap(PlayerSwapHandItemsEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onDrop(final PlayerDropItemEvent e) {
        if (!Lobby.builders.contains(e.getPlayer())) {
            e.getItemDrop().remove();
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onItemInventoryClick(final InventoryClickEvent e) {
        if (!Lobby.builders.contains(e.getWhoClicked())) {
            e.setCancelled(
                    e.getClickedInventory() != null &&
                            e.getClickedInventory().getType() == InventoryType.PLAYER
            );
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onItemDrag(final InventoryDragEvent e) {
        if (!Lobby.builders.contains(e.getWhoClicked())) {
            e.setCancelled(
                    e.getInventory().getType() == InventoryType.PLAYER
            );
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (plugin.getConfig().getBoolean("Hunger")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (plugin.getConfig().getBoolean("Damage")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
            e.setCancelled(true);
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent e) {
        if (plugin.getConfig().getBoolean("Item-Use")) {
            if (!Lobby.builders.contains(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}