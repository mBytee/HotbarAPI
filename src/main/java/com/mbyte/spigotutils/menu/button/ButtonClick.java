package com.mbyte.spigotutils.menu.button;

import com.mbyte.spigotutils.menu.Menu;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@Getter
public class ButtonClick {
    /**
     * The underlying click event.
     */
    @Getter(AccessLevel.NONE)
    private final InventoryClickEvent event;

    /**
     * The menu that was clicked.
     */
    private final Menu menu;

    /**
     * The button that was clicked, this can be
     * null if the click was outside the inventory.
     */
    private final Button button;

    /**
     * Creates a new button click, extracting
     * values from the underlying click event.
     *
     * @param event  The click event.
     * @param button The clicked button, can be null.
     * @param menu   The clicked menu.
     */
    public ButtonClick(InventoryClickEvent event, Button button, Menu menu) {
        this.event = event;
        this.menu = menu;
        this.button = button;
    }

    /**
     * @return The type of click.
     */
    public ClickType getType() {
        return event.getClick();
    }

    /**
     * @return The slot of the click.
     */
    public int getSlot() {
        return event.getSlot();
    }
}
