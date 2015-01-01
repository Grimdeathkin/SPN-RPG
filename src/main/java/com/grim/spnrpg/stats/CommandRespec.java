package com.grim.spnrpg.stats;

import com.grim.spnrpg.SpnRpg;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRespec implements CommandExecutor{
    
    public SpnRpg plugin;
    public Economy economy;
    
    public CommandRespec(SpnRpg plugin) {
        this.plugin = plugin;
        economy = plugin.getEcon();
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("respec")){
            Player player = (Player) sender;
            if(player.hasPermission("spnrpg.stats.respec")){
                EconomyResponse economyResponse = economy.withdrawPlayer(player, plugin.getConfig().getDouble("respec multiplier") * plugin.getPlayerStat(player).getLevel());
                if(economyResponse.transactionSuccess()){
                    player.sendMessage(ChatColor.RED + "You have reset your stats");
                }else{
                    player.sendMessage(ChatColor.RED + "You can not afford that, it costs: ");
                }                
            }
        }
        return false;
    }
}
