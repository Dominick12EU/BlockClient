package it.dominick.blockclient.handler;

import it.dominick.blockclient.BlockClient;
import it.dominick.blockclient.manager.BrandManager;
import org.bukkit.entity.Player;

public class BrandCheckHandler {

    private final BlockClient plugin;
    private final BrandManager brandManager;

    public BrandCheckHandler(BlockClient plugin, BrandManager brandManager) {
        this.plugin = plugin;
        this.brandManager = brandManager;
    }

    public void handleBrandCheck(Player player, String brand) {
        brandManager.setPlayerBrand(player.getName(), brand);
        if (!plugin.getConfig().getBoolean("enable")) return;
        if (plugin.getConfig().getBoolean("geyser-support") && player.getName().contains(plugin.getConfig().getString("geyser-prefix"))) return;
        if (plugin.getConfig().getString("mode", "blacklist").equals("blacklist")) {
            for (String blockedBrand : plugin.getConfig().getStringList("blocked-brands")) {
                if (brand.toLowerCase().contains(blockedBrand.toLowerCase())) {
                    if (player.hasPermission("blockclient.bypass")) return;
                    String kickMsg = plugin.getConfig().getString("kick-message");
                    assert kickMsg != null;
                    player.kickPlayer(kickMsg.replace("%player%", player.getName()).replace("%brand%", brand));
                    plugin.getLogger().info(plugin.getConfig().getString("console-log").replace("%player%", player.getName()).replace("%brand%", brand));
                    return;
                }
            }
        } else if (plugin.getConfig().getString("mode", "whitelist").equals("whitelist")) {
            for (String blockedBrand : plugin.getConfig().getStringList("blocked-brands")) {
                if (brand.toLowerCase().contains(blockedBrand.toLowerCase()))
                    return;
            }
            if (player.hasPermission("blockclient.bypass")) return;
            String kickMsg = plugin.getConfig().getString("kick-message");
            assert kickMsg != null;
            player.kickPlayer(kickMsg);
            plugin.getLogger().info(plugin.getConfig().getString("console-log").replace("%player%", player.getName()).replace("%brand%", brand));
        }
    }
}
