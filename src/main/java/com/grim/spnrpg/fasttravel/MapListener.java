package com.grim.spnrpg.fasttravel;

import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class MapListener implements Listener {

    private HashMap<String, String> cityMaterial = new HashMap<String, String>();
    private MapMenu mapMenu;
    private ConfigHandler configHandler = new ConfigHandler();
    private FileConfiguration config = configHandler.getWarps();

    public void onRightClick(PlayerInteractEvent event){
        Action action = event.getAction();
        if(!(action == Action.RIGHT_CLICK_BLOCK)) return;
        FileConfiguration config = Main.plugin.getConfig();
        Material block = event.getClickedBlock().getType();
        Player player = event.getPlayer();
        if(block == null) return;
        Material mat = Material.valueOf(config.getString("mapblock"));
        if(block == mat){
            event.setCancelled(true);
            mapMenu = new MapMenu(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        FileConfiguration config = Main.plugin.getConfig();
        List<String> planets = config.getStringList("cities");
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        Inventory inv = event.getInventory();
        for(String name : planets){
            cityMaterial.put(config.getString(name + ".item"), name);
        }
        if(inv.getName().equals("Fast Travel")){
            event.setCancelled(true);
            String currentItem = event.getCurrentItem().getType().toString();
            String planet = cityMaterial.get(currentItem);
            if(cityMaterial.containsKey(currentItem)){
                event.setCancelled(true);
                player.closeInventory();
                if(player.hasPermission("edenstarmap.access." + planet)){
                    teleportPlayer(planet, player);
                }else{
                    player.sendMessage(ChatColor.RED + "You can not go there");
                }
            }
        }
    }

    public void teleportPlayer(String planet, Player player){
        if(player.hasPermission("EdenStarMap.access." + planet)){
            Location loc = getLocation(planet);
            player.teleport(loc);
        }else{
            player.sendMessage("You can not visit this planet");
        }
    }

    public Location getLocation(String planet){

        World world = Bukkit.getServer().getWorld(config.getString(planet + ".world"));
        double x = config.getDouble(planet + ".x");
        double y = config.getDouble(planet + ".y");
        double z = config.getDouble(planet + ".z");
        return new Location(world, x, y ,z);
    }
}
