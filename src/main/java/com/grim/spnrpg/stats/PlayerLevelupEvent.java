package com.grim.spnrpg.stats;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLevelupEvent extends Event{

    public static final HandlerList handlers = new HandlerList();
    private Player player;
    private int newLevel;
    private Stats stats;

    public PlayerLevelupEvent(Player player, int newLevel, Stats stats) {
        this.player = player;
        this.newLevel = newLevel;
        this.stats = stats;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public Stats getStats(){
        return stats;
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
