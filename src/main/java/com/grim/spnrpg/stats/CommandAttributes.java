package com.grim.spnrpg.stats;

import com.grim.spnrpg.IconMenu;
import com.grim.spnrpg.SpnRpg;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class CommandAttributes implements CommandExecutor {

    private SpnRpg plugin;
    private static IconMenu attributeMenu;

    public CommandAttributes(final SpnRpg plugin){
        this.plugin = plugin;
        
        attributeMenu = new IconMenu("Attribute selector", 9, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                String name = event.getName();
                if(name.equalsIgnoreCase("stamina")){
                    increaseStam(event.getPlayer());
                }else if(name.equalsIgnoreCase("strength")){
                    increaseStr(event.getPlayer());
                }else if(name.equalsIgnoreCase("dexterity")){
                    increaseDex(event.getPlayer());
                }else if(name.equalsIgnoreCase("agility")){
                    increaseAgi(event.getPlayer());
                }else if(name.equalsIgnoreCase("respect")){
                    Stats stats = plugin.getPlayerStat(event.getPlayer());
                    stats.setStamina(20).setStrength(1).setAgility(1).setAgility(1);
                    plugin.setAttributePoints(event.getPlayer(), stats.getLevel() * 3);
                    plugin.updatePlayerStats(event.getPlayer(), stats);
                }
            }
        }, plugin);
        attributeMenu.setOption(1, new Wool(DyeColor.BLUE).toItemStack(), "Stamina", ChatColor.RED + "Increase Health");
        attributeMenu.setOption(3, new Wool(DyeColor.RED).toItemStack(), "Strength", ChatColor.RED + "Increase Melee Damage");
        attributeMenu.setOption(5, new Wool(DyeColor.GREEN).toItemStack(), "Dexterity", ChatColor.RED + "Increase Ranged Damage");
        attributeMenu.setOption(7, new Wool(DyeColor.YELLOW).toItemStack(), "Agility", ChatColor.RED + "Increase Crit Chance");
        attributeMenu.setOption(11, new ItemStack(Material.BOOK), "Respec", ChatColor.RED + "Cost: " + plugin.getConfig().getDouble("stats.respeccost"));
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("attributes")){
            Player player = (Player) commandSender;
            int attributePoints = plugin.getAttributePoints(player);
            player.sendMessage(ChatColor.RED + "You have " + attributePoints + " points left to spend.");
            attributeMenu.open(player);
        }
        return false;
    }

    private void increaseStam(Player player){
        int attributePoints = plugin.getAttributePoints(player) - 1;
        Stats stats = plugin.getPlayerStat(player);
        stats.addStamina(1);
        plugin.updatePlayerStats(player, stats);
        plugin.setAttributePoints(player, attributePoints);
        player.sendMessage(ChatColor.RED + "You have " + attributePoints + " points left to spend.");
    }

    private void increaseStr(Player player){
        int attributePoints = plugin.getAttributePoints(player) - 1;
        Stats stats = plugin.getPlayerStat(player);
        stats.addStrength(1);
        plugin.updatePlayerStats(player, stats);
        plugin.setAttributePoints(player, attributePoints);
        player.sendMessage(ChatColor.RED + "You have " + attributePoints + " points left to spend.");
    }

    private void increaseDex(Player player){
        int attributePoints = plugin.getAttributePoints(player) - 1;
        Stats stats = plugin.getPlayerStat(player);
        stats.addDexterity(1);
        plugin.updatePlayerStats(player, stats);
        plugin.setAttributePoints(player, attributePoints);
        player.sendMessage(ChatColor.RED + "You have " + attributePoints + " points left to spend.");
    }

    private void increaseAgi(Player player){
        int attributePoints = plugin.getAttributePoints(player) - 1;
        Stats stats = plugin.getPlayerStat(player);
        stats.addAgility(1);
        plugin.updatePlayerStats(player, stats);
        plugin.setAttributePoints(player, attributePoints);
        player.sendMessage(ChatColor.RED + "You have " + attributePoints + " points left to spend.");
    }
}
