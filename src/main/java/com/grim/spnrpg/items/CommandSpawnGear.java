package com.grim.spnrpg.items;

import com.grim.spnrpg.SpnRpg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawnGear implements CommandExecutor {

    private SpnRpg plugin;

    public CommandSpawnGear(SpnRpg plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("spawngear")){
            if(args.length == 1){
                Player player = (Player) sender;
                player.getInventory().addItem(plugin.getRandomItemGenerator().getRandomItem(Integer.valueOf(args[0])));
            }
        }
        return false;
    }
}
