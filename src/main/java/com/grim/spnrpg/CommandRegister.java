package com.grim.spnrpg;

import com.grim.spnrpg.run.CommandRun;

public class CommandRegister {

    public CommandRegister(Main plugin){
        plugin.getCommand("run").setExecutor(new CommandRun());
    }
}
