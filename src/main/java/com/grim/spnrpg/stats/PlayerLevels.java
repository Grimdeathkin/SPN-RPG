package com.grim.spnrpg.stats;

import com.darkblade12.particleeffect.ParticleEffect;
import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.Main;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerLevels implements Listener{

    private final Main plugin;
    private final FileConfiguration configuration;
    private final WorldGuardPlugin wgPlugin;
    private final ConfigHandler configHandler;

    public PlayerLevels(Main plugin) {
        this.plugin = plugin;
        configHandler = new ConfigHandler("xp.yml");
        configuration = configHandler.getConfig();
        wgPlugin = plugin.getWorldGuard();
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null) return;
        Entity entity = event.getEntity();
        Player player = event.getEntity().getKiller();

        Stats playerStat = plugin.getPlayerStat(player);
        Double xp = getXP(entity);
        playerStat.addXP(xp);
        plugin.updatePlayerStats(player, playerStat);

        //Call custom XP change event
        PlayerXPChangeEvent playerXPChangeEvent = new PlayerXPChangeEvent(player, xp);
        Bukkit.getServer().getPluginManager().callEvent(playerXPChangeEvent);
    }

    @EventHandler
    public void onLevelUp(PlayerLevelupEvent event){
        Player player = event.getPlayer();
        plugin.setAttributePoints(player, plugin.getAttributePoints(player) + 3);
        ParticleEffect.FIREWORKS_SPARK.display(1f, 1f, 1f, 1f, 1, player.getLocation(), 5);
        player.playSound(player.getLocation(), Sound.ARROW_HIT, 1f, 1f);
        player.sendMessage(ChatColor.RED + "Congratulations, you  are now level" + (event.getNewLevel() + 1));
        player.sendMessage(ChatColor.RED + "You have been given 3 attribute points, speed them with /attributes");
        plugin.setAttributePoints(player, plugin.getAttributePoints(player) + 3);
        plugin.updatePlayerStats(player, event.getStats());
    }

    private Double getXP(Entity entity){
        for(ProtectedRegion region : getRegions(entity)){
            //Check if a xp value is assigned to the region
            if(configuration.contains(region.getId())){
                return configuration.getDouble(region.getId());
            }
        }
        //Return 0 if nothing found
        return 0D;
    }

    private ApplicableRegionSet getRegions(Entity entity){
        RegionManager manager = wgPlugin.getRegionManager(entity.getWorld());
        return manager.getApplicableRegions(entity.getLocation());
    }
}
