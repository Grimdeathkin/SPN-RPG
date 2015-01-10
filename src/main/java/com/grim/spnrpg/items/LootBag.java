package com.grim.spnrpg.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootBag {
    
    private Inventory inventory;
    private Player player;
    
    public LootBag(Player player) {
        inventory = Bukkit.createInventory(player, 9, "Loot");
        this.player = player;
    }
    
    public boolean isFull(){
        if(inventory.firstEmpty() == -1){
            return true;            
        } return false;
    }
    
    public void addItem(ItemStack itemStack){
        if(isFull() == true){
            player.sendMessage(ChatColor.RED + "Your inventory is full, you lost a piece of loot");            
        } else{
            inventory.addItem(itemStack);            
        }            
    }
}
