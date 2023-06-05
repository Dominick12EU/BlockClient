package it.dominick.blockclient.events;

import it.dominick.blockclient.manager.BrandManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final BrandManager brandManager;

    public PlayerQuitListener(BrandManager brandManager) {
        this.brandManager = brandManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        brandManager.removePlayerBrand(e.getPlayer().getName());
    }
}
