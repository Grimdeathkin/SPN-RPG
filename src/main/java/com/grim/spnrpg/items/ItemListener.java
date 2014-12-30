package com.grim.spnrpg.items;

import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.Main;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemListener implements Listener {

    private final Main plugin;
    private final WorldGuardPlugin wgPlugin;

    public ItemListener(Main plugin){
        this.plugin = plugin;
        wgPlugin = plugin.getWorldGuard();
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event){
        LivingEntity entity = event.getEntity();
        List<ItemStack> drops = event.getDrops();
        //Check if entity is a player, we don't want to edit players drops
        if(entity instanceof Player) return;

        //We need to remove the usual drops
        drops.clear();
        int itemLevel = getItemLevel(entity);

        //If the item level isn't defined nothing will drop
        if(itemLevel == 0){
            return;
        }

        //TODO Create item algorithm
    }

    //Get the item level stored in the config
    private int getItemLevel(Entity entity){
        FileConfiguration regionItemLevels = new ConfigHandler("regionItemLevels.yml").getConfig();
        for(ProtectedRegion region : getRegions(entity)){
            //Check if an item level is assigned to the region
            if(regionItemLevels.contains(region.getId())){
                return regionItemLevels.getInt(region.getId());
            }
        }
        //Return 0 if nothing found
        return 0;
    }

    //Get the regions in the entity died in
    private ApplicableRegionSet getRegions(Entity entity){
        RegionManager manager = wgPlugin.getRegionManager(entity.getWorld());
        ApplicableRegionSet set = manager.getApplicableRegions(entity.getLocation());
        return set;
    }
}
