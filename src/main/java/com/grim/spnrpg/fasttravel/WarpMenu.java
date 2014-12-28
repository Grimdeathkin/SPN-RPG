package com.grim.spnrpg.fasttravel;

import com.grim.spnrpg.ConfigHandler;
import com.grim.spnrpg.IconMenu;
import com.grim.spnrpg.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WarpMenu {

    private final Main plugin;
    private IconMenu iconMenu;

    public WarpMenu(Main plugin){
        this.plugin = plugin;

        initWarpMenu();
    }

    public void initWarpMenu(){
        FileConfiguration config = Main.plugin.getConfig();

        iconMenu = new IconMenu(config.getString("warp.name"), config.getInt("warp.size"), new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                warpPlayer(event.getPlayer(), event.getName());
            }
        }, plugin);
        setOptions(iconMenu);
    }

    public void warpPlayer(Player player, String name){
        FileConfiguration warpList = new ConfigHandler("warps.yml").getConfig();
        if(player.hasPermission("spnrpg.fasttravel." + name)) {
            player.teleport(getLocation(warpList, name));
        }else{
            player.sendMessage(ChatColor.RED + "You can not fast travel there");
        }
    }

    public Location getLocation(FileConfiguration warpList, String name){
        double x = warpList.getDouble(name + ".x");
        double y = warpList.getDouble(name + ".y");
        double z = warpList.getDouble(name + ".z");
        World world = Bukkit.getWorld(warpList.getString(name + ".world"));

        return new Location(world, x, y, z);
    }

    public void setOptions(IconMenu iconMenu){
        FileConfiguration warpList = new ConfigHandler("warps.yml").getConfig();
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
