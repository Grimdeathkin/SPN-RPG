package com.grim.spnrpg.stats;

public class Stats {

    private int stamina;
    private int strength;
    private int dexterity;
    private int agility;
    private int level;
    private double xp;

    public Stats(int stamina, int strength, int dexterity, int agility, int level, double xp) {
        this.stamina = stamina;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.level = level;
        this.xp = xp;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public void addXP(double xp){
        this.xp += xp;
    }

    public int getAgility() {
        return agility;
    }

    public int getStamina() {
        return stamina;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getLevel() {
        return level;
    }

    public double getXp() {
        return xp;
    }
}
