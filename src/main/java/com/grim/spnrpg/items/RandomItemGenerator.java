package com.grim.spnrpg.items;

import com.grim.spnrpg.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RandomItemGenerator {
    
    private final Random random;

    public RandomItemGenerator() {
        random = new Random();
    }

    public ItemStack getRandomItem(int itemLevel) {
        int randomNumber = random.nextInt(5);
        int type = random.nextInt(2);
        if (type == 0) {
            if (itemLevel < 100) {
                switch (randomNumber) {
                    case 0:
                        return setStats(new ItemStack(Material.LEATHER_HELMET), itemLevel, type);
                    case 1:
                        return setStats(new ItemStack(Material.LEATHER_CHESTPLATE), itemLevel, type);
                    case 2:
                        return setStats(new ItemStack(Material.LEATHER_LEGGINGS), itemLevel, type);
                    case 3:
                        return setStats(new ItemStack(Material.LEATHER_BOOTS), itemLevel, type);
                    case 4:
                        return setStats(new ItemStack(Material.BOW), itemLevel, type);
                }
            } else {
                switch (randomNumber) {
                    case 0:
                        return setStats(new ItemStack(Material.CHAINMAIL_HELMET), itemLevel, type);
                    case 1:
                        return setStats(new ItemStack(Material.CHAINMAIL_CHESTPLATE), itemLevel, type);
                    case 2:
                        return setStats(new ItemStack(Material.CHAINMAIL_LEGGINGS), itemLevel, type);
                    case 3:
                        return setStats(new ItemStack(Material.CHAINMAIL_BOOTS), itemLevel, type);
                    case 4:
                        return setStats(new ItemStack(Material.BOW), itemLevel, type);
                }
            }
        } else if (type == 1) {
            if (itemLevel < 100) {
                switch (randomNumber) {
                    case 0:
                        return setStats(new ItemStack(Material.IRON_HELMET), itemLevel, type);
                    case 1:
                        return setStats(new ItemStack(Material.IRON_CHESTPLATE), itemLevel, type);
                    case 2:
                        return setStats(new ItemStack(Material.IRON_LEGGINGS), itemLevel, type);
                    case 3:
                        return setStats(new ItemStack(Material.IRON_BOOTS), itemLevel, type);
                    case 4:
                        return setStats(new ItemStack(Material.STONE_SWORD), itemLevel, type);
                }
            } else {
                switch (randomNumber) {
                    case 0:
                        return setStats(new ItemStack(Material.DIAMOND_HELMET), itemLevel, type);
                    case 1:
                        return setStats(new ItemStack(Material.DIAMOND_CHESTPLATE), itemLevel, type);
                    case 2:
                        return setStats(new ItemStack(Material.DIAMOND_LEGGINGS), itemLevel, type);
                    case 3:
                        return setStats(new ItemStack(Material.DIAMOND_BOOTS), itemLevel, type);
                    case 4:
                        return setStats(new ItemStack(Material.IRON_SWORD), itemLevel, type);
                }
            }
        }return null;
    }
    
    private ItemStack setStats(ItemStack itemStack, int itemLevel, int type){
        ItemBuilder itemBuilder = new ItemBuilder(itemStack);
        int statBudget = (int) (0.5*itemLevel - 2);
        int[] stats = {0, 0, 0, 0}; //Stamina, strength, agility, dexterity
        itemBuilder.lore(ChatColor.DARK_GREEN + "Item level: " + itemLevel);
        boolean isWeapon =  itemStack.getType() == Material.STONE_SWORD || itemStack.getType() == Material.IRON_SWORD || itemStack.getType() == Material.BOW; //No stam on weapons
        for(int i = 0; i <= statBudget; i++){
            stats = rollStats(stats, type, isWeapon);
        }
        if(stats[0] != 0){
            itemBuilder.lore("Stamina: " + stats[0]);
        }if(stats[1] != 0){
            itemBuilder.lore("Strength: " + stats[1]);
        }if(stats[2] != 0){
            itemBuilder.lore("Dexterity: " + stats[2]);
        }if(stats[3] != 0){
            itemBuilder.lore("Agility: " + stats[3]);
        }
        return itemBuilder.build();
    }
    
    private int[] rollStats(int[] stats, int type, boolean isWeapon){
        int randomNumber;
        if(isWeapon){
            randomNumber = random.nextInt(2)+1;
        }else {
            randomNumber = random.nextInt(3);
        }
        if(randomNumber == 0){
            stats[0] += 1;
        }else if(randomNumber == 1){
            if(type == 1){
                stats[1] += 1;
            }else if(type == 0){
                stats[2] += 1;
            }
        }else if(randomNumber == 2){
            stats[3] += 1;
        }return stats;
    }
}