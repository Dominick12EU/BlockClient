package it.dominick.blockclient.manager;

import it.dominick.blockclient.BlockClient;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class BrandManager {
    private final BlockClient plugin;
    private final FileConfiguration config;

    private final HashMap<String, String> playerBrands;

    public BrandManager(BlockClient plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.playerBrands = new HashMap<>();
    }

    public void setPlayerBrand(String playerName, String brand) {
        playerBrands.put(playerName, brand);
    }

    public String getPlayerBrand(String playerName) {
        return playerBrands.get(playerName);
    }

    public void removePlayerBrand(String playerName) {
        playerBrands.remove(playerName);
    }

    public void clearPlayerBrands() {
        playerBrands.clear();
    }
}
