package com.grim.spnrpg.fasttravel;

import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.IconMenu;
import com.grim.spnrpg.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WarpMenu {

    private final Main plugin;
    private IconMenu iconMenu;
    private FileConfiguration warpList;
    private Economy economy;
    
    public WarpMenu(Main plugin){
        this.plugin = plugin;
        warpList = new ConfigHandler("warps.yml").getConfig();
        economy = plugin.getEcon();
        initWarpMenu();
    }

    private void initWarpMenu(){
        FileConfiguration config = Main.plugin.getConfig();

        iconMenu = new IconMenu(config.getString("warp.name"), config.getInt("warp.size"), new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                EconomyResponse economyResponse = economy.withdrawPlayer(event.getPlayer(), warpList.getDouble(event.getName() + ".cost"));
                if(economyResponse.transactionSuccess()){
                    warpPlayer(event.getPlayer(), event.getName());
                }else{
                    event.getPlayer().sendMessage(ChatColor.RED + "You can not afford that");
                }
            }
        }, plugin);
        setOptions(iconMenu);
    }

    private void warpPlayer(Player player, String name){
        if(player.hasPermission("spnrpg.fasttravel." + name)) {
            player.teleport(getLocation(name));
        }else{
            player.sendMessage(ChatColor.RED + "You can not fast travel there");
        }
    }

    private Location getLocation(String name){
        double x = warpList.getDouble(name + ".x");
        double y = warpList.getDouble(name + ".y");
        double z = warpList.getDouble(name + ".z");
        World world = Bukkit.getWorld(warpList.getString(name + ".world"));

        return new Location(world, x, y, z);
    }

    private void setOptions(IconMenu iconMenu){
        List<String> warps = warpList.getStringList("warps");
        for(String warp : warps){
            int i = 0;
            ItemStack icon = new ItemStack(Material.valueOf(warpList.getString(warp + ".icon")));
            String name = warpList.getString(warp + ".name");
            iconMenu.setOption(i, icon, name);
        }
    }

    public IconMenu getIconMenu() {
        return iconMenu;
    }
}
