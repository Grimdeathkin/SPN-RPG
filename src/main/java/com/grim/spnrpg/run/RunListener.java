package com.grim.spnrpg.run;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class RunListener implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(player.hasPotionEffect(PotionEffectType.SPEED)){
            player.removePotionEffect(PotionEffectType.SPEED);
        }
    }
}
