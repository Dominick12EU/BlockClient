package it.dominick.blockclient.handler;

import it.dominick.blockclient.BlockClient;
import it.dominick.blockclient.manager.BrandManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHandler {

    private final BlockClient plugin;
    private final BrandManager brandManager;

    String prefix;


    public CommandHandler(BlockClient plugin, BrandManager brandManager) {
        this.plugin = plugin;
        this.brandManager = brandManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("blockclient")) {
            if (args.length == 0) {
                if (sender.hasPermission("blockclient.usage")) {
                    sender.sendMessage("§6§m----------------");
                    sender.sendMessage("§e§lBlockClient §7by Dominick12");
                    sender.sendMessage("§7");
                    sender.sendMessage("§eUsage §6»");
                    sender.sendMessage("§6§l● §eCheck §7(playername)");
                    sender.sendMessage("§6§l● §eReload");
                    sender.sendMessage("§6§m----------------");
                } else {
                    sender.sendMessage("§e§lBlockClient §7by Dominick12");
                }
            } else {
                if (args[0].equalsIgnoreCase("check")) {
                    if (sender.hasPermission("blockclient.usage")) {
                        if (!(args.length > 1)) {
                            prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"));
                            sender.sendMessage(prefix + plugin.getConfig().getString("specify-player-name"));
                        } else {
                            String playerName = args[1];
                            String brand = brandManager.getPlayerBrand(playerName);
                            if (brand != null) {
                                sender.sendMessage(prefix + plugin.getConfig().getString("check-succesful")
                                        .replace("%player%", playerName).replace("%brand%", brand));
                            } else {
                                sender.sendMessage(prefix + plugin.getConfig().getString("check-failed")
                                        .replace("%player%", playerName));
                            }
                        }
                    } else {
                        sender.sendMessage(prefix + plugin.getConfig().getString("no-permission"));
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("blockclient.usage")) {
                        plugin.reloadConfig();
                        sender.sendMessage(prefix + plugin.getConfig().getString("config-reload"));
                    } else {
                        sender.sendMessage(prefix + plugin.getConfig().getString("no-permission"));
                    }
                }
            }
            return true;
        }
        return false;
    }
}
