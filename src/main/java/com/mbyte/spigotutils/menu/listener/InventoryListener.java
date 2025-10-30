package com.mbyte.spigotutils.menu.listener;

import com.mbyte.spigotutils.menu.Menu;
import com.mbyte.spigotutils.menu.MenuHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@RequiredArgsConstructor
public class InventoryListener implements Listener {
    /**
     * The menu handler that registered the listener.
     */
    private final MenuHandler handler;

    /**
     * Sends a click event to a player's menu if they have one
     * open, cancelling the click, so they can't pick up the item.
     *
     * @param event The click event.
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        handler.ifOpen((Player) event.getWhoClicked(), menu -> {
            event.setCancelled(true);
            menu.click(event);
        });
    }

    /**
     * When an inventory is closed, the menu
     * gets closed and remove from the open menus map.
     *
     * @param event The close event.
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        handler.ifOpen((Player) event.getPlayer(), menu ->
                menu.close(false)
        );
    }

    /**
     * Closes a players menu when they leave the game.
     *
     * @param event The quit event.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        handler.ifOpen(event.getPlayer(), Menu::close);
    }
}
