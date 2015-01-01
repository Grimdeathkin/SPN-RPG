package com.grim.spnrpg.items;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawnGear implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("spawngear")){
            if(args.length == 1){
                Player player = (Player) sender;
                player.getInventory().addItem(new RandomItemGenerator().getRandomItem(Integer.valueOf(args[0])));
            }
        }
        return false;
    }
}
