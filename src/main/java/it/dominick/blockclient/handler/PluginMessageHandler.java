package it.dominick.blockclient.handler;

import it.dominick.blockclient.BlockClient;
import it.dominick.blockclient.manager.BrandManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;

public class PluginMessageHandler implements PluginMessageListener {

    private final BlockClient plugin;
    private final BrandManager brandManager;

    public PluginMessageHandler(BlockClient plugin, BrandManager brandManager) {
        this.plugin = plugin;
        this.brandManager = brandManager;
    }

    public void registerIncomingPluginChannel() {
        String[] div = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        String version = div[1];

        if (Integer.parseInt(version) < 13) {
            Messenger messenger = Bukkit.getMessenger();
            messenger.registerIncomingPluginChannel(plugin, "MC|Brand", this);
            plugin.getLogger().info("Registered 1.12+ listener");
        } else {
            Messenger messenger = Bukkit.getMessenger();
            messenger.registerIncomingPluginChannel(plugin, "minecraft:brand", this);
            plugin.getLogger().info("Registered 1.13+ listener");
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] msg) {
        String brand = new String(msg, StandardCharsets.UTF_8).substring(1);
        brandManager.setPlayerBrand(p.getName(), brand);
        if (!plugin.getConfig().getBoolean("enable")) return;
        if (plugin.getConfig().getBoolean("geyser-support") && p.getName().contains(plugin.getConfig().getString("geyser-prefix"))) return;
        if (plugin.getConfig().getString("mode", "blacklist").equals("blacklist")) {
            for (String blockedBrand : plugin.getConfig().getStringList("blocked-brands")) {
                if (brand.toLowerCase().contains(blockedBrand.toLowerCase())) {
                    if (p.hasPermission("blockclient.bypass")) return;
                    String kickMsg = plugin.getConfig().getString("kick-message");
                    assert kickMsg != null;
                    p.kickPlayer(kickMsg.replace("%player%", p.getName()).replace("%brand%", brand));
                    plugin.getLogger().info(plugin.getConfig().getString("console-log").replace("%player%", p.getName()).replace("%brand%", brand));
                    return;
                }
            }
        } else if (plugin.getConfig().getString("mode", "whitelist").equals("whitelist")) {
            for (String blockedBrand : plugin.getConfig().getStringList("blocked-brands")) {
                if (brand.toLowerCase().contains(blockedBrand.toLowerCase()))
                    return;
            }
            if (p.hasPermission("blockclient.bypass")) return;
            String kickMsg = plugin.getConfig().getString("kick-message");
            assert kickMsg != null;
            p.kickPlayer(kickMsg);
            plugin.getLogger().info(plugin.getConfig().getString("console-log").replace("%player%", p.getName()).replace("%brand%", brand));
        }
    }
}
