package com.grim.spnrpg.fasttravel;

import com.grim.spnrpg.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MapMenu {

    private static Inventory inventory = Bukkit.createInventory(null, 27, "Fast Travel");
    private ConfigHandler configHandler = new ConfigHandler();
    private FileConfiguration config;

    //Create the map for the player
    public MapMenu(Player player){
        populateMenu(player);
        showMenu(player);
        config = configHandler.getWarps();
    }

    //Populates the warp menu
    private void populateMenu(Player player){
        //Create warp config file
        List<String> cities = config.getStringList("cities"); //Get list of defined warp locations
        for(String name : cities){
            createDisplay(player, "spnrpg.warp.access." + name, Material.valueOf(config.getString(name + ".item")), inventory, config.getInt(name + ".invlocation"), name, "");
            ItemStack nextPage = new ItemStack(Material.WRITTEN_BOOK, 1);
            ItemMeta im = nextPage.getItemMeta();
            im.setDisplayName(ChatColor.YELLOW + "Shop");
            nextPage.setItemMeta(im);
        }
    }

    //Create the items in the inventory
    private void createDisplay(Player player, String permission, Material material, Inventory inv, int Slot, String name, String lore){
        if(player.hasPermission(permission)){
            name = ChatColor.GREEN + name;
            lore = ChatColor.GREEN + "You can go here";
        }else{
            name = ChatColor.RED + name;
            lore = ChatColor.RED + "You can not go here";
        }
        //Sets item meta for a menu icon
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> Lore = new ArrayList<String>();
        Lore.add(lore);
        meta.setLore(Lore);
        item.setItemMeta(meta);
        inv.setItem(Slot, item);
    }

    public void showMenu(Player player){
        //Shows the menu to the player
        player.openInventory(inventory);
    }

    public Inventory getMenu(){
        return inventory;
    }
}
