package net.ttfl.code;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class TiaCustomOnline extends Plugin {
    private AmountCounter amountCounter;
    private Configuration config;

    @Override
    public void onEnable() {
        loadConfig();

        getProxy().getPluginManager().registerCommand(this, new CommandHandler(this, "tiacustomonline"));
        getProxy().getPluginManager().registerCommand(this, new CommandHandler(this, "tco"));
        getProxy().getPluginManager().registerListener(this, new PingListener(this));

        amountCounter = new AmountCounter(this);
    }

    @Override
    public void onDisable() {
    }

    public AmountCounter getCount() {
        return amountCounter;
    }

    private void loadConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        loadConfig();
    }

    public void reloadConfig() {
        loadConfig();
    }

    public Configuration getConfig(){
        return config;
    }
}
