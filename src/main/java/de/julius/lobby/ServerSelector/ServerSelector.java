package de.julius.lobby.ServerSelector;

import de.julius.lobby.Lobby;
import de.julius.lobby.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
import java.util.List;
import java.util.Map;

public class ServerSelector implements Listener {

    public static Lobby plugin;

    public ServerSelector(Lobby plugin) {
        this.plugin = plugin;
    }

    private static String getConfigString(String path) {
        return plugin.getConfig().getString(path);
    }
    private static Boolean getConfigBoolean(String path) {
        return plugin.getConfig().getBoolean(path);
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
            if (e.getView().getTitle().equals("§9§lɴᴇʙᴜʟᴀʀ §f§lɴᴇᴛᴡᴏʀᴋ")) {
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
        if (e.getRawSlot() == 11) {
            if (e.getCurrentItem().getType() == Material.getMaterial(getConfigString("server-selector.server-one.item"))) {
                Lobby.teleportPlayerToServer(player, plugin.getConfig().getString("server-selector.server-one.ip"), plugin, e);
            }
        }
        if (e.getRawSlot() == 13) {
            if (e.getCurrentItem().getType() == Material.getMaterial(getConfigString("server-selector.server-two.item"))) {
                Lobby.teleportPlayerToServer(player, plugin.getConfig().getString("server-selector.server-two.ip"), plugin, e);
            }
        }
        if (e.getRawSlot() == 15) {
            if (e.getCurrentItem().getType() == Material.getMaterial(getConfigString("server-selector.server-three.item"))) {
                Lobby.teleportPlayerToServer(player, plugin.getConfig().getString("server-selector.server-three.ip"), plugin, e);
            }
        }
    }

    public static ItemStack getSelector() {
        ItemStack ServerSelector = new ItemStack(Material.getMaterial(getConfigString("server-selector.item.item")));
        ItemMeta ServerSelectorMeta = ServerSelector.getItemMeta();

        ServerSelectorMeta.setDisplayName(getConfigString("server-selector.item.item-name"));
        ArrayList<String> LoreList = new ArrayList<>();
        LoreList.add(getConfigString("server-selector.item.item-lore"));

        ServerSelectorMeta.setLore(LoreList);
        ServerSelector.setItemMeta(ServerSelectorMeta);

        return ServerSelector;
    }

    public void openGUI(Player player) {
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();

        Inventory gui = Bukkit.createInventory(null, 27, "§9§lɴᴇʙᴜʟᴀʀ §f§lɴᴇᴛᴡᴏʀᴋ");

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

        //Item One -------
        if (getConfigBoolean("server-selector.server-one.active")) {
            if (Material.getMaterial(getConfigString("server-selector.server-one.item")) != null && getConfigString("server-selector.server-one.item-name") != null && !(getConfigString("server-selector.server-one.ip") == null || getConfigString("server-selector.server-one.ip").equals(""))) {
                if (Material.getMaterial(getConfigString("server-selector.server-one.item")) != Material.AIR) {
                    if (plugin.getConfig().getInt("server-selector.server-one.item-slot") >= 0) {
                        if (!(getConfigString("server-selector.server-one.item-lore").equals(""))) {
                            integerItemStackHashMap.put(plugin.getConfig().getInt("server-selector.server-one.item-slot"), new ItemBuilder(Material.getMaterial(getConfigString("server-selector.server-one.item"))).setDisplayname(getConfigString("server-selector.server-one.item-name")).setLocalizedName(getConfigString("server-selector.server-one.ip")).setLore(getConfigString("server-selector.server-one.item-lore")).build());
                            for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
                                gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
                            }
                        }else {
                            integerItemStackHashMap.put(plugin.getConfig().getInt("server-selector.server-one.item-slot"), new ItemBuilder(Material.getMaterial(getConfigString("server-selector.server-one.item"))).setDisplayname(getConfigString("server-selector.server-one.item-name")).setLocalizedName(getConfigString("server-selector.server-one.ip")).build());
                            for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
                                gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
                            }
                        }
                    }else {
                        buildEmptyItem(11, integerItemStackHashMap, gui);
                    }
                }else {
                    buildEmptyItem(11, integerItemStackHashMap, gui);
                }
            }else {
                buildEmptyItem(11, integerItemStackHashMap, gui);
            }
        }else {
            buildEmptyItem(11, integerItemStackHashMap, gui);
        }

        //Item two -------
        if (getConfigBoolean("server-selector.server-two.active")) {
            if (Material.getMaterial(getConfigString("server-selector.server-two.item")) != null && getConfigString("server-selector.server-two.item-name") != null && !(getConfigString("server-selector.server-two.ip") == null || getConfigString("server-selector.server-two.ip").equals(""))) {
                if (Material.getMaterial(getConfigString("server-selector.server-two.item")) != Material.AIR) {
                    if (plugin.getConfig().getInt("server-selector.server-two.item-slot") >= 0) {
                        if (!(getConfigString("server-selector.server-two.item-lore").equals(""))) {
                            integerItemStackHashMap.put(plugin.getConfig().getInt("server-selector.server-two.item-slot"), new ItemBuilder(Material.getMaterial(getConfigString("server-selector.server-two.item"))).setDisplayname(getConfigString("server-selector.server-two.item-name")).setLocalizedName(getConfigString("server-selector.server-two.ip")).setLore(getConfigString("server-selector.server-two.item-lore")).build());
                            for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
                                gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
                            }
                        }else {
                            integerItemStackHashMap.put(plugin.getConfig().getInt("server-selector.server-two.item-slot"), new ItemBuilder(Material.getMaterial(getConfigString("server-selector.server-two.item"))).setDisplayname(getConfigString("server-selector.server-two.item-name")).setLocalizedName(getConfigString("server-selector.server-two.ip")).build());
                            for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
                                gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
                            }
                        }
                    }else {
                        buildEmptyItem(15, integerItemStackHashMap, gui);
                    }
                }else {
                    buildEmptyItem(13, integerItemStackHashMap, gui);
                }
            }else {
                buildEmptyItem(13, integerItemStackHashMap, gui);
            }
        }else {
            buildEmptyItem(13, integerItemStackHashMap, gui);
        }

        /*
        integerItemStackHashMap.put(13, new ItemBuilder(Material.CHERRY_SAPLING).setDisplayname(ChatColor.BLUE + "" + ChatColor.BOLD + "ɴᴇʙᴜʟᴀʀꜱᴍᴘ").setLocalizedName("nebularsmp").setLore(ChatColor.GRAY + "ᴋʟɪᴄᴋᴇ " + ChatColor.BLUE + "ʜɪᴇʀ " + ChatColor.GRAY + "ᴜᴍ ᴢᴜ " + ChatColor.BLUE + "ɴᴇʙᴜʟᴀʀꜱᴍᴘ " + ChatColor.GRAY + "ᴢᴜ ɢᴇʟᴀɴɢᴇɴ.").build());
        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }
        */

        //Item three -------
        if (getConfigBoolean("server-selector.server-three.active")) {
            if (Material.getMaterial(getConfigString("server-selector.server-three.item")) != null && getConfigString("server-selector.server-three.item-name") != null && !(getConfigString("server-selector.server-three.ip") == null || getConfigString("server-selector.server-three.ip").equals(""))) {
                if (Material.getMaterial(getConfigString("server-selector.server-three.item")) != Material.AIR) {
                    if (plugin.getConfig().getInt("server-selector.server-three.item-slot") >= 0) {
                        if (!(getConfigString("server-selector.server-three.item-lore").equals(""))) {
                            integerItemStackHashMap.put(plugin.getConfig().getInt("server-selector.server-three.item-slot"), new ItemBuilder(Material.getMaterial(getConfigString("server-selector.server-three.item"))).setDisplayname(getConfigString("server-selector.server-three.item-name")).setLocalizedName(getConfigString("server-selector.server-three.ip")).setLore(getConfigString("server-selector.server-three.item-lore")).build());
                            for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
                                gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
                            }
                        }else {
                            integerItemStackHashMap.put(plugin.getConfig().getInt("server-selector.server-three.item-slot"), new ItemBuilder(Material.getMaterial(getConfigString("server-selector.server-three.item"))).setDisplayname(getConfigString("server-selector.server-three.item-name")).setLocalizedName(getConfigString("server-selector.server-three.ip")).build());
                            for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
                                gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
                            }
                        }
                    }else {
                        buildEmptyItem(15, integerItemStackHashMap, gui);
                    }
                }else {
                    buildEmptyItem(15, integerItemStackHashMap, gui);
                }
            }else {
                buildEmptyItem(15, integerItemStackHashMap, gui);
            }
        }else {
            buildEmptyItem(15, integerItemStackHashMap, gui);
        }

        /*
        integerItemStackHashMap.put(15, new ItemBuilder(Material.CACTUS).setDisplayname(ChatColor.BLUE + "" + ChatColor.BOLD + "ᴄᴏᴍɪɴɢ ꜱᴏᴏɴ").setLocalizedName("nebularsmp").build());
        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }
        */

        player.openInventory(gui);
    }

    private static void buildEmptyItem(Integer slot, HashMap<Integer, ItemStack> integerItemStackHashMap, Inventory gui) {

        integerItemStackHashMap.put(slot, new ItemBuilder(Material.AIR).build());
        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            gui.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }

    }

}