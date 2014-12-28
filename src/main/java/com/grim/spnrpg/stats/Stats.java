package com.grim.spnrpg.stats;

public class Stats {

    private int stamina;
    private int strength;
    private int dexterity;
    private int agility;

    public Stats(int stamina, int strength, int dexterity, int agility) {
        this.stamina = stamina;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
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

}
