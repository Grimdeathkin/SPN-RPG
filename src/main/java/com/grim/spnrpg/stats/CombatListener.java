package com.grim.spnrpg.stats;

import com.grim.spnrpg.Main;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class CombatListener implements Listener {

    private Main plugin;

    public CombatListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();
        //Set damage for melee combat
        if(damager instanceof Player){
            Player player = (Player) damager;
            int[] stats = getGearStats(getArmourStats(player.getInventory()), getSwordStats(player.getItemInHand()));
            event.setDamage(event.getDamage() + (stats[1] * 0.5));
        }
        //Set damage for ranged combat
        else if(damager instanceof Arrow){
            Arrow arrow = (Arrow) damager;
            if(arrow.getShooter() instanceof Player){
                Player player = (Player) arrow.getShooter();
                int[] stats = getGearStats(getArmourStats(player.getInventory()), getSwordStats(player.getItemInHand()));
                event.setDamage(event.getDamage() + (stats[2] * 0.5));
            }
        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        int stamina = plugin.getPlayerStat(player).getStamina();
        int[] stats = getArmourStats(player.getInventory());
        stamina = stats[0] + stamina;
        setPlayerHealth(player, stamina);
    }

    private int[] getArmourStats(PlayerInventory inventory){
        int[] stats = {0, 0, 0, 0};
        ItemStack[] armourContents = inventory.getArmorContents();
        for(ItemStack armour : armourContents){
            List<String> lore = armour.getItemMeta().getLore();
            for (String stat : lore){
                if(stat.contains("Stamina: ")){
                    stats[0] += Integer.valueOf(stat.replace("Stamina: ", ""));
                }
                if(stat.contains("Strength: ")){
                    stats[1] += Integer.valueOf(stat.replace("Strength: ", ""));
                }
                if(stat.contains("Dexterity: ")){
                    stats[2] += Integer.valueOf(stat.replace("Dexterity: ", ""));
                }
                if(stat.contains("Agility: ")){
                    stats[3] += Integer.valueOf(stat.replace("Agility: ", ""));
                }
            }
        } return stats;
    }

    private int[] getSwordStats(ItemStack itemStack){
        int[] stats = {0, 0, 0, 0};
        List<String> lore = itemStack.getItemMeta().getLore();
        for (String stat : lore) {
            if (stat.contains("Stamina: ")) {
                stats[0] += Integer.valueOf(stat.replace("Stamina: ", ""));
            }
            if (stat.contains("Strength: ")) {
                stats[1] += Integer.valueOf(stat.replace("Strength: ", ""));
            }
            if (stat.contains("Dexterity: ")) {
                stats[2] += Integer.valueOf(stat.replace("Dexterity: ", ""));
            }
            if (stat.contains("Agility: ")) {
                stats[3] += Integer.valueOf(stat.replace("Agility: ", ""));
            }
        } return stats;
    }

    private int[] getGearStats(int[] armour, int[] weapon){
        int[] stats = {0, 0, 0, 0};
        for(int i = 0; i == 3; i++){
            stats[i] = armour[i] + weapon[i];
        }
        return stats;
    }

    private void setPlayerHealth(Player player, int stamina){
        player.setMaxHealth(stamina);
        player.setHealth(stamina);
        player.setHealthScale(20);
    }
}
