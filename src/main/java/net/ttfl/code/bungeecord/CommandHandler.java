package net.ttfl.code.bungeecord;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandHandler extends Command {
    private TiaCustomOnline plugin;

    public CommandHandler(TiaCustomOnline plugin, String name) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponent("Usage: /" + getName() + " <args>"));
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("bungee.command.reload")) {
                sender.sendMessage(new TextComponent("I'm sorry, but you don't have permission to use this command."));
                return;
            }

            File configFile = new File(plugin.getDataFolder(), "config.yml");
            if (configFile.exists()) {
                plugin.saveDefaultConfig();
            }
            plugin.reloadConfig();
            sender.sendMessage(new TextComponent("[TiaCustomOnline] Reloaded."));
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("reload");
        }
        return completions;
    }
}
