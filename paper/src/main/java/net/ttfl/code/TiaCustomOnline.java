package net.ttfl.code;

import org.bukkit.plugin.java.JavaPlugin;

public class TiaCustomOnline extends JavaPlugin {
    private AmountCounter amountCounter;

    @Override
    public void onEnable(){
        saveDefaultConfig();
        CommandHandler commandHandler = new CommandHandler(this);
        getCommand("tiacustomonline").setExecutor(commandHandler);
        getCommand("tco").setExecutor(commandHandler);
        amountCounter = new AmountCounter(this);
    }

    @Override
    public void onDisable(){
    }

    public AmountCounter getCount(){
        return amountCounter;
    }
}
