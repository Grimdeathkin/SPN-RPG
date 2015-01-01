package com.grim.spnrpg.stats;

import com.grim.spnrpg.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRespec implements CommandExecutor{
    
    public Main plugin;
    public Economy economy;
    
    public CommandRespec(Main plugin) {
        this.plugin = plugin;
        economy = plugin.getEcon();
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("respec")){
            Player player = (Player) sender;
            if(player.hasPermission("spnrpg.stats.respec")){
                EconomyResponse economyResponse = economy.withdrawPlayer(player, plugin.getConfig().getDouble("respec multiplier") * plugin.getPlayerStat(player).getLevel());
                if(economyResponse.transactionSuccess()){
                                        
                }else{
                    player.sendMessage("You can not afford that, it costs: ");
                }                
            }
        }
        return false;
    }
}