package net.ttfl.code;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {
    private TiaCustomOnline plugin;

    public CommandHandler(TiaCustomOnline plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(label.equalsIgnoreCase("tiacustomonline") || label.equalsIgnoreCase("tco")){
            if(args[0].equalsIgnoreCase("reload")){
                if(sender instanceof Player && !sender.isOp()){
                    sender.sendMessage("I'm sorry, but you don't have permission to use this command.");
                    return true;
                }

                File configFile = new File(plugin.getDataFolder(), "config.yml");
                if(configFile.exists()){
                    plugin.saveDefaultConfig();
                }
                plugin.reloadConfig();
                sender.sendMessage("[TiaCustomOnline] Reloaded.");
            }

            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        list.add("reload");
        return list;
    }
}
