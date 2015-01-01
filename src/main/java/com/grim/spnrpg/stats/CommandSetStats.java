package com.grim.spnrpg.stats;

import com.grim.spnrpg.SpnRpg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetStats implements CommandExecutor{

    private SpnRpg plugin;
    
    public CommandSetStats(SpnRpg plugin){
        this.plugin = plugin;
            }
    
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("setstats")){
            if(args.length == 3){
                 if(Bukkit.getServer().getPlayer(args[0]).isOnline()){
                     Player player = Bukkit.getServer().getPlayer(args[0]);
                     if(args[1].equalsIgnoreCase("stamina")){
                         Stats stats = plugin.getPlayerStat(player);
                         stats.setStamina(Integer.valueOf(args[2]));
                         plugin.updatePlayerStats(player, stats);
                     }else if(args[1].equalsIgnoreCase("strength")){
                         Stats stats = plugin.getPlayerStat(player);
                         stats.setStrength(Integer.valueOf(args[2]));
                         plugin.updatePlayerStats(player, stats);
                     }else if(args[1].equalsIgnoreCase("dexterity")){
                         Stats stats = plugin.getPlayerStat(player);
                         stats.setDexterity(Integer.valueOf(args[2]));
                         plugin.updatePlayerStats(player, stats);
                     }else if(args[1].equalsIgnoreCase("agility")){
                         Stats stats = plugin.getPlayerStat(player);
                         stats.setAgility(Integer.valueOf(args[2]));
                         plugin.updatePlayerStats(player, stats);
                     }else if(args[1].equalsIgnoreCase("level")){
                         Stats stats = plugin.getPlayerStat(player);
                         int oldLevel = stats.getLevel();
                         stats.setLevel(Integer.valueOf(args[2]));
                         plugin.updatePlayerStats(player, stats);

                         PlayerLevelupEvent playerLevelupEvent = new PlayerLevelupEvent(player, Integer.valueOf(args[2]), oldLevel, stats);
                         Bukkit.getPluginManager().callEvent(playerLevelupEvent);
                     }else if(args[1].equalsIgnoreCase("xp")){
                         Stats stats = plugin.getPlayerStat(player);
                         stats.setXp(Integer.valueOf(args[2]));
                         plugin.updatePlayerStats(player, stats);
                         
                         PlayerXPChangeEvent playerXPChangeEvent = new PlayerXPChangeEvent(player, Double.valueOf(args[2]));
                         Bukkit.getPluginManager().callEvent(playerXPChangeEvent);
                     }
                 }
            }
        }
        return false;
    }
}