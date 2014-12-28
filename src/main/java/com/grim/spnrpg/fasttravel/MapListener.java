package com.grim.spnrpg.fasttravel;

import com.grim.spnrpg.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class MapListener implements Listener {

    private Main plugin;

    public MapListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        FileConfiguration config = Main.plugin.getConfig();

        if(!(block.getType() == Material.valueOf(config.getString("warpblock")))) return;
        plugin.getWarpMenu().open(player);
    }

}
