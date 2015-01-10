package com.grim.spnrpg.stats;

import com.darkblade12.particleeffect.ParticleEffect;
import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.SpnRpg;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

public class PlayerListener implements Listener {

    private SpnRpg plugin;
    private final FileConfiguration configuration;
    private final WorldGuardPlugin wgPlugin;

    public PlayerListener(SpnRpg plugin){
        this.plugin = plugin;
        this.plugin = plugin;
        ConfigHandler configHandler = new ConfigHandler("xp.yml");
        configuration = configHandler.getConfig();
        wgPlugin = plugin.getWorldGuard();
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        ConfigHandler configHandler = new ConfigHandler("userdata/" + player.getUniqueId().toString() + ".yml");
        FileConfiguration configuration = configHandler.getConfig();
        if(!configuration.contains("stats")){
            setDefaultStats(configHandler);
        }
        plugin.updatePlayerStats(player, getStats(configuration));
        displayLevel(player);
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event){
        String uuid = event.getPlayer().getUniqueId().toString();
        Stats stats = plugin.getPlayerStat(uuid);
        plugin.removePlayerStats(event.getPlayer());
    }


    @EventHandler
    public void onXPChange(PlayerXPChangeEvent event){
        Player player = event.getPlayer();
        Stats stats = plugin.getPlayerStat(player);
        int level = stats.getLevel();
        Double xp = event.getNewXP();
        Double nextLevel = Math.pow(2, level)*1.5 + 1000 * level;
        if(xp >= nextLevel){
            stats.addLevel(1);

            PlayerLevelupEvent playerLevelupEvent = new PlayerLevelupEvent(player, level + 1, level, stats);
            Bukkit.getServer().getPluginManager().callEvent(playerLevelupEvent);
        }
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
        int levelDifference = event.getNewLevel() - event.getOldLevel();
        plugin.setAttributePoints(player, plugin.getAttributePoints(player) + 3 * levelDifference);
        ParticleEffect.FIREWORKS_SPARK.display(1f, 1f, 1f, 1f, 1, player.getLocation(), 5);
        player.playSound(player.getLocation(), Sound.ARROW_HIT, 1f, 1f);
        player.sendMessage(ChatColor.RED + "Congratulations, you  are now level" + (event.getNewLevel() + 1));
        player.sendMessage(ChatColor.RED + "You have been given " + 3 * levelDifference + " attribute points, speed them with /attributes");
        plugin.setAttributePoints(player, plugin.getAttributePoints(player) + 3);
        plugin.updatePlayerStats(player, event.getStats());
        displayLevel(player);
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

    private void setDefaultStats(ConfigHandler configurationHandler){
        FileConfiguration configuration = configurationHandler.getConfig();
        configuration.addDefault("stats.level", 1);
        configuration.addDefault("stats.stamina", 20);
        configuration.addDefault("stats.strength", 1);
        configuration.addDefault("stats.dexterity", 1);
        configuration.addDefault("stats.agility", 1);
        configuration.addDefault("stats.xp", 0);
        configuration.options().copyDefaults(true);
        configurationHandler.saveConfig();
    }

    private Stats getStats(FileConfiguration configuration){
        int stamina = configuration.getInt("stats.stamina");
        int strength = configuration.getInt("stats.strength");
        int dexterity = configuration.getInt("stats.dexterity");
        int agility = configuration.getInt("stats.agility");
        int level = configuration.getInt("stats.level");
        double xp = configuration.getDouble("stats.xp");

        return new Stats(stamina, strength, dexterity, agility, level, xp);
    }

    private void displayLevel(Player player){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("level", "dummy");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName(" :Level");
        Score score = objective.getScore(player);
        score.setScore(plugin.getPlayerStat(player).getLevel());
        player.setScoreboard(scoreboard);
    }

}
