package com.grim.spnrpg;

import com.grim.spnrpg.fasttravel.WarpListener;
import com.grim.spnrpg.items.ItemListener;
import com.grim.spnrpg.run.RunListener;
import com.grim.spnrpg.stats.PlayerListener;
import org.bukkit.plugin.PluginManager;

public class ListenerRegister {

    public ListenerRegister(Main plugin){
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new RunListener(), plugin);
        pluginManager.registerEvents(new WarpListener(plugin), plugin);
        pluginManager.registerEvents(new PlayerListener(plugin), plugin);
        pluginManager.registerEvents(new ItemListener(plugin), plugin);
    }
}
