package com.ricodev.spigotutils.hotbar;

import de.tr7zw.nbtapi.NBT;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@RequiredArgsConstructor
public class ItemHandler implements Listener {

    private final JavaPlugin instance;

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        final ItemStack itemStack = event.getItem();

        if (itemStack != null) {
            final MItem<?> item = this.checkItem(itemStack);

            if (item != null) {
                final Action action = event.getAction();
                if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                    item.onLeftClick(event);
                } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    item.onRightClick(event);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent event) {
        final ItemStack itemStack = event.getCurrentItem();

        if (itemStack != null && itemStack.getType() != Material.AIR) {
            final MItem<?> item = this.checkItem(itemStack);

            if (item != null) {
                item.onInventoryClick(event);
            }
        }
    }

    private MItem<?> checkItem(ItemStack itemStack) {
        if (itemStack == null) return null;

        final String[] key = new String[1];
        NBT.get(itemStack, nbt -> {
            key[0] = nbt.getString(ItemManager.ITEM_NBT_KEY);
        });
        if (key[0] != null) {
            return ItemManager.getInstance().getItem(key[0]);
        }
        return null;
    }

}
