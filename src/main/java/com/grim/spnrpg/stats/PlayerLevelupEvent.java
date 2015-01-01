package com.grim.spnrpg.stats;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelupEvent extends Event{

    public static final HandlerList handlers = new HandlerList();
    private Player player;
    private int newLevel;
    private Stats stats;
    private int oldLevel;

    public PlayerLevelupEvent(Player player, int newLevel, int oldLevel, Stats stats) {
        this.player = player;
        this.newLevel = newLevel;
        this.stats = stats;
        this.oldLevel = oldLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public int getOldLevel() {
        return oldLevel;
    }

    public Stats getStats(){
        return stats;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
}
