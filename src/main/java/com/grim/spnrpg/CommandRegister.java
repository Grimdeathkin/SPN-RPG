package com.grim.spnrpg;

import com.grim.spnrpg.run.CommandRun;
import com.grim.spnrpg.stats.CommandAttributes;
import com.grim.spnrpg.stats.CommandRespec;
import com.grim.spnrpg.stats.CommandSetStats;

public class CommandRegister {

    public CommandRegister(SpnRpg plugin){
        plugin.getCommand("run").setExecutor(new CommandRun());
        plugin.getCommand("attribute").setExecutor(new CommandAttributes(plugin));
        plugin.getCommand("respec").setExecutor(new CommandRespec(plugin));
        plugin.getCommand("setstats").setExecutor(new CommandSetStats(plugin));
    }
}
