package com.mbyte.spigotutils.menu.layer.impl;

import com.mbyte.spigotutils.menu.Menu;
import com.mbyte.spigotutils.menu.button.Button;
import com.mbyte.spigotutils.menu.layer.Layer;
import com.mbyte.spigotutils.menu.pagination.PaginationSlot;

import java.util.HashMap;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public class BackgroundLayer extends Layer {
    /**
     * Creates a new background layer.
     *
     * @param menu The menu the layer belongs to.
     */
    public BackgroundLayer(Menu menu) {
        super(menu, new HashMap<>());
    }

    /**
     * Returns the name of the background layer.
     *
     * @return The background layer name.
     */
    @Override
    public String getName() {
        return "background";
    }

    /**
     * Adds a button to the next available slot,
     * checking slots in the foreground layer as well.
     *
     * @param button The button to add.
     * @param other  If the foreground layer should be checked.
     */
    @Override
    public void add(Button button, boolean other) {
        add(button, other ? getMenu().getForeground() : null);
    }

    /**
     * Checks if a button to be set is invalid,
     * if so sends a warning message.
     * Also prevents pagination slots from
     * being added to the background layer.
     *
     * @param button The button to check
     * @param index  The index to check
     * @return If the button or index is invalid.
     */
    @Override
    protected boolean isInvalid(Button button, int index) {
        if (super.isInvalid(button, index)) return true;

        if (button instanceof PaginationSlot) {
            warning("pagination slot", button, index);
            return true;
        }

        return false;
    }

}
