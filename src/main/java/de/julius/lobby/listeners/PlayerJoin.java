package de.julius.lobby.listeners;

import de.julius.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().getInventory().setItem(4, getSelector());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onItemMove(final InventoryClickEvent e) {
            e.setCancelled(
                    e.getClickedInventory() != null &&
                            e.getWhoClicked().getOpenInventory().getTitle().equals(ChatColor.BLUE + "Server Selector")
            );
        }

    @EventHandler
    public void onInventoryClick(PlayerInteractEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) return;

        if (event.getItem().getType() == Material.CLOCK) {
            openGUI(player);
            event.setCancelled(true);
        }
    }

    public static ItemStack getSelector() {
        ItemStack ServerSelector = new ItemStack(Material.CLOCK);
        ItemMeta ServerSelectorMeta = ServerSelector.getItemMeta();

        ServerSelectorMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Server Selector");
        ArrayList<String> LoreList = new ArrayList<String>();
        LoreList.add(ChatColor.GRAY + "» Select The Server You");
        LoreList.add(ChatColor.GRAY + "» Wish To Play On");

        ServerSelectorMeta.setLore(LoreList);
        ServerSelector.setItemMeta(ServerSelectorMeta);

        return ServerSelector;
    }

    public void openGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 36, ChatColor.BLUE + "Server Selector");

        ItemStack server1 = new ItemStack(Material.DIAMOND);
        ItemMeta server1Meta = server1.getItemMeta();
        server1Meta.setDisplayName(ChatColor.GREEN + "Server 1");
        server1.setItemMeta(server1Meta);
        gui.setItem(0, server1);

        player.openInventory(gui);
    }
}