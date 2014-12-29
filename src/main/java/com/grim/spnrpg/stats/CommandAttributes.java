package com.grim.spnrpg.stats;

import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.IconMenu;
import com.grim.spnrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;

public class CommandAttributes implements CommandExecutor {

    private Main plugin;
    private static IconMenu attributeMenu;

    public CommandAttributes(Main plugin){
        this.plugin = plugin;
        attributeMenu = new IconMenu("Attribute selector", 9, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                String name = event.getName();
                if(name.equalsIgnoreCase("Stamina")){
                    increaseStam(event.getPlayer());
                }else if(name.equalsIgnoreCase("Strength")){
                    increaseStr(event.getPlayer());
                }else if(name.equalsIgnoreCase("Dexterity")){
                    increaseDex(event.getPlayer());
                }else if(name.equalsIgnoreCase("Agility")){
                    increaseAgi(event.getPlayer());
                }
            }
        }, plugin);
        attributeMenu.setOption(1, new Wool(DyeColor.BLUE).toItemStack(), "Stamina", ChatColor.RED + "Increase Health");
        attributeMenu.setOption(3, new Wool(DyeColor.RED).toItemStack(), "Strength", ChatColor.RED + "Increase Melee Damage");
        attributeMenu.setOption(5, new Wool(DyeColor.GREEN).toItemStack(), "Dexterity", ChatColor.RED + "Increase Ranged Damage");
        attributeMenu.setOption(7, new Wool(DyeColor.YELLOW).toItemStack(), "Agility", ChatColor.RED + "Increase Crit Chance");
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("attributes")){
            Player player = (Player) commandSender;
            int attributePoints = 0;
            FileConfiguration config = new ConfigHandler(player.getUniqueId().toString() + ".yml").getConfig();
            attributePoints = config.getInt("attributepoints");
            player.sendMessage(ChatColor.RED + "You have " + attributePoints + " points left to spend.");
            attributeMenu.open(player);
        }
        return false;
    }

    private void increaseStam(Player player){
        Stats stats = plugin.getPlayerStat(player);
        stats.addStamina(1);
        plugin.updatePlayerStats(player, stats);
    }

    private void increaseStr(Player player){
        Stats stats = plugin.getPlayerStat(player);
        stats.addStrength(1);
        plugin.updatePlayerStats(player, stats);
    }

    private void increaseDex(Player player){
        Stats stats = plugin.getPlayerStat(player);
        stats.addDexterity(1);
        plugin.updatePlayerStats(player, stats);
    }

    private void increaseAgi(Player player){
        Stats stats = plugin.getPlayerStat(player);
        stats.addAgility(1);
        plugin.updatePlayerStats(player, stats);
    }
}
