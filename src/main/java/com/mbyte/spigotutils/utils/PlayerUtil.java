package com.mbyte.spigotutils.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public class PlayerUtil {

    /**
     * Add a given item to a player inventory
     *
     * @param itemStack The {@link ItemStack} to add
     * @param player    The concerned {@link Player}
     * @return <code>true</code> if the items have been given, else it will be <code>false</code> because the player doesn't have enough space
     */
    public static boolean addItemInPlayerInventory(ItemStack itemStack, Player player) {
        final Inventory inventory = player.getInventory();

        if (hasEnoughSlots(player, itemStack)) {
            inventory.addItem(itemStack);
            return true;
        }
        return false;
    }

    /**
     * Check if a player has enough slots to store an amount of items
     *
     * @param player The concerned {@link Player}
     * @param item   The item to store
     * @return <code>true</code> if the player has enough slots, else it will be <code>false</code>
     */
    public static boolean hasEnoughSlots(Player player, ItemStack item) {
        final PlayerInventory inventory = player.getInventory();
        final int amount = item.getAmount();
        final int slotsNeeded = (int) Math.ceil((double) amount / 64);

        int count = 0;
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null || (itemStack.equals(item) && itemStack.getAmount() + amount <= 64)) {
                count++;
            }
        }

        if (slotsNeeded == 0) {
            return false;
        }

        return count >= slotsNeeded;
    }

}
