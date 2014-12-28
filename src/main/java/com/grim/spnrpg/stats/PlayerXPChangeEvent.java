package com.grim.spnrpg.stats;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerXPChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Double newXP;

    public PlayerXPChangeEvent(Player player, Double newXP) {
        this.player = player;
        this.newXP = newXP;
    }

    public Player getPlayer() {
        return player;
    }

    public Double getNewXP() {
        return newXP;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
