package it.dominick.blockclient;

import it.dominick.blockclient.events.PlayerQuitListener;
import it.dominick.blockclient.handler.CommandHandler;
import it.dominick.blockclient.handler.PluginMessageHandler;
import it.dominick.blockclient.manager.BrandManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockClient extends JavaPlugin {

    private BrandManager brandManager;
    private PluginMessageHandler pluginMessageHandler;
    private CommandHandler commandHandler;
    private PlayerQuitListener playerQuitListener;

    @Override
    public void onEnable() {
        getLogger().info("----------------------------------------");
        getLogger().info("");
        getLogger().info(getDescription().getName() + " Enabled!");
        getLogger().info("Version: " + getDescription().getVersion());
        getLogger().info("");
        getLogger().info("----------------------------------------");

        saveDefaultConfig();

        new BrandManager(this);
        new PluginMessageHandler(this, brandManager);
        new CommandHandler(this, brandManager);
        new PlayerQuitListener(brandManager);

        pluginMessageHandler.registerIncomingPluginChannel();

        getCommand("blockclient").setExecutor((CommandExecutor) commandHandler);

        getServer().getPluginManager().registerEvents((Listener) pluginMessageHandler, this);
        getServer().getPluginManager().registerEvents(playerQuitListener, this);
    }


    @Override
    public void onDisable() {
        brandManager.clearPlayerBrands();
    }
}
