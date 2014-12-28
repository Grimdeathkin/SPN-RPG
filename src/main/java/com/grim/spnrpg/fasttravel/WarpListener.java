package com.grim.spnrpg.fasttravel;

import com.grim.spnrpg.Main;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class WarpListener implements Listener {

    private Main plugin;
    private WorldGuardPlugin wgPlugin;

    public WarpListener(Main plugin){
        this.plugin = plugin;
        wgPlugin = plugin.getWorldGuard();
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        FileConfiguration config = Main.plugin.getConfig();

        if(!(block.getType() == Material.valueOf(config.getString("warpblock")))) return;
        String name = getCityRegion(player);
        if(!player.hasPermission("spnrpg.access." + name) && name != null){
            plugin.getPerms().playerAdd(player, "spnrpg.access." + name);
        }
        plugin.getWarpMenu().open(player);
    }


    public String getCityRegion(Player player){
        RegionManager manager = wgPlugin.getRegionManager(player.getWorld());
        ApplicableRegionSet set = manager.getApplicableRegions(player.getLocation());

        for(ProtectedRegion region : set){
            if(region.getId().startsWith("city")){
                //Returns name of the city
                return region.getId().replace("city","");
            }
        }return null;
    }

}
