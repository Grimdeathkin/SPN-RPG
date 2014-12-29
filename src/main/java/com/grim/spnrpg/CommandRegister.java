package com.grim.spnrpg;

import com.grim.spnrpg.run.CommandRun;
import com.grim.spnrpg.stats.CommandAttributes;

public class CommandRegister {

    public CommandRegister(Main plugin){
        plugin.getCommand("run").setExecutor(new CommandRun());
        plugin.getCommand("attribute").setExecutor(new CommandAttributes(plugin));
    }
}
