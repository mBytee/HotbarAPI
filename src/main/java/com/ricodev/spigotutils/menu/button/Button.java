package com.ricodev.spigotutils.menu.button;

import org.bukkit.inventory.ItemStack;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public abstract class Button {
    /**
     * Gets the button's display item stack, this can
     * be null if the button should not be displayed.
     *
     * @return The buttons icon.
     */
    public abstract ItemStack getIcon();

    /**
     * Called when the button is clicked,
     * this can be ignored by the onClick
     * handler in the button's menu.
     *
     * @param click The button click.
     */
    public void onClick(ButtonClick click) {
    }
}