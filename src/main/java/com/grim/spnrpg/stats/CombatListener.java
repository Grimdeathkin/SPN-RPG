package com.grim.spnrpg.stats;

import com.grim.spnrpg.SpnRpg;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.Random;

public class CombatListener implements Listener {

    private SpnRpg plugin;

    public CombatListener(SpnRpg plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();
        //Set damage for melee combat
        if(damager instanceof Player){
            Player player = (Player) damager;
            int[] stats = getTotalStats(getArmourStats(player.getInventory(), player), getWeaponStats(player.getItemInHand()), player);
            if(isCrit(stats[3])){
                event.setDamage((event.getDamage() + (stats[1] * 0.5)) * 1.5);
            }else{
                event.setDamage(event.getDamage() + (stats[1] * 0.5));
            }
        }
        //Set damage for ranged combat
        else if(damager instanceof Arrow){
            Arrow arrow = (Arrow) damager;
            if(arrow.getShooter() instanceof Player){
                Player player = (Player) arrow.getShooter();
                int[] stats = getTotalStats(getArmourStats(player.getInventory(), player), getWeaponStats(player.getItemInHand()), player);
                if(isCrit(stats[3])){
                    event.setDamage((event.getDamage() + (stats[2] * 0.5)) * 1.5);
                }else{
                    event.setDamage(event.getDamage() + (stats[2] * 0.5));
                }
            }
        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        int stamina = plugin.getPlayerStat(player).getStamina();
        int[] stats = getArmourStats(player.getInventory(), player);
        stamina = stats[0] + stamina;
        setPlayerHealth(player, stamina);
    }

    @EventHandler
    public void onItemEquip(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        int stamina = plugin.getPlayerStat(player).getStamina();
        int[] stats = getArmourStats(player.getInventory(), player);
        stamina = stats[0] + stamina;
        setPlayerHealth(player, stamina);
    }

    private int[] getArmourStats(PlayerInventory inventory, Player player){
        int[] stats = {0, 0, 0, 0};
        ItemStack[] armourContents = inventory.getArmorContents();
        if(armourContents == null) return stats;
        for(ItemStack armour : armourContents){
            if(armour.getTypeId() != 0){
                List<String> lore = armour.getItemMeta().getLore();
                if(lore != null){
                    for (String stat : lore) {
                        if (stat.contains("Stamina: ")) {
                            stats[0] += Integer.valueOf(stat.replace("Stamina: ", ""));
                            player.sendMessage("DEBUG:" +  stats[0]);
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
                    }
                }
            }
        } return stats;
    }

    private int[] getWeaponStats(ItemStack itemStack){
        int[] stats = {0, 0, 0, 0};
        if(itemStack == null) return stats;
        if(itemStack.getItemMeta() == null) return stats;
        List<String> lore = itemStack.getItemMeta().getLore();
        if(lore == null) return stats;
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

    private int[] getTotalStats(int[] armour, int[] weapon, Player player){
        int[] stats = {0, 0, 0, 0};
        Stats playerStats = plugin.getPlayerStat(player);
        int[] playerStat = {playerStats.getStamina(), playerStats.getStrength(), playerStats.getDexterity(), playerStats.getAgility()};
        for(int i = 0; i <= 3; i++){
            stats[i] = armour[i] + weapon[i] + playerStat[i];
        }
        return stats;
    }

    private void setPlayerHealth(Player player, int stamina){
        player.setMaxHealth(stamina);
        player.setHealthScale(20);
        player.sendMessage(player.getHealth() + ", " + player.getHealthScale());
    }

    private boolean isCrit(int agility){
        int rolls = agility/2;
        if(rolls == 0){
            rolls = 1;
        }
        Random random = new Random();
        for(int i = 1; i == rolls; i++){
            int randomInt = random.nextInt(1000);
            if(randomInt == 69){
                return true;
            }
        }return false;
    }
}
