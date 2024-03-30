package de.julius.lobby.ServerSelector;

import de.julius.lobby.Lobby;
import de.julius.lobby.util.ItemBuilder;
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
import java.util.HashMap;
import java.util.Map;

public class ServerSelector implements Listener {

    public final Lobby plugin;

    public ServerSelector(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().getInventory().setItem(4, getSelector());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onItemMove(final InventoryClickEvent e) {
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getView().getTitle().equals("§9§lNebular§8 » §lServers")) {
                e.setCancelled(true);
            }
        }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) return;

        if (event.getItem().getType() == Material.CLOCK) {
            openGUI(player);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        // Teleport Player to Server

        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if (e.getCurrentItem().getType() == Material.CHERRY_SAPLING) {
            Lobby.teleportPlayerToServer(player, "nebularsmp", plugin);
        }
    }

    public static ItemStack getSelector() {
        ItemStack ServerSelector = new ItemStack(Material.CLOCK);
        ItemMeta ServerSelectorMeta = ServerSelector.getItemMeta();

        ServerSelectorMeta.setDisplayName("§c§lServer Selector");
        ArrayList<String> LoreList = new ArrayList<String>();
        LoreList.add(ChatColor.GRAY + "» Select The Server You");
        LoreList.add(ChatColor.GRAY + "» Wish To Play On");

        ServerSelectorMeta.setLore(LoreList);
        ServerSelector.setItemMeta(ServerSelectorMeta);

        return ServerSelector;
    }

    public void openGUI(Player player) {
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();

        Inventory gui = Bukkit.createInventory(null, 27, "§9§lNebular§8 » §lServers");

        // GUI Items

        integerItemStackHashMap.put(0, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayname("§b").build());
        integerItemStackHashMap.put(1, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§b").build());
        integerItemStackHashMap.put(7, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§b").build());
        integerItemStackHashMap.put(8, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayname("§b").build());
        integerItemStackHashMap.put(9, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§b").build());
        integerItemStackHashMap.put(17, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§b").build());
        integerItemStackHashMap.put(18, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayname("§b").build());
        integerItemStackHashMap.put(19, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§b").build());
        integerItemStackHashMap.put(25, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§b").build());
        integerItemStackHashMap.put(26, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayname("§b").build());

        integerItemStackHashMap.put(13, new ItemBuilder(Material.CHERRY_SAPLING).setDisplayname(ChatColor.BLUE + "" + ChatColor.BOLD + "NebularSMP").setLocalizedName("nebularsmp").setLore(ChatColor.GRAY + "Klicke " + ChatColor.BLUE + "hier " + ChatColor.GRAY + "um zu " + ChatColor.BLUE + "NebularSMP " + ChatColor.GRAY + "zu gelangen.").build());
        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }

        player.openInventory(gui);
    }
}