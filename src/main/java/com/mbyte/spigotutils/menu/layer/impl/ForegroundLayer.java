package com.mbyte.spigotutils.menu.layer.impl;

import com.mbyte.spigotutils.menu.Menu;
import com.mbyte.spigotutils.menu.button.Button;
import com.mbyte.spigotutils.menu.layer.Layer;

import java.util.TreeMap;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public class ForegroundLayer extends Layer {

    /**
     * Creates a new foreground layer.
     *
     * @param menu The menu the layer belongs to.
     */
    public ForegroundLayer(Menu menu) {
        super(menu, new TreeMap<>());
    }

    /**
     * Returns the name of the foreground layer.
     *
     * @return The foreground layer name.
     */
    @Override
    public String getName() {
        return "foreground";
    }

    /**
     * Adds a button to the next available slot,
     * checking slots in the background layer as well.
     *
     * @param button The button to add.
     * @param other  If the background layer should be checked.
     */
    @Override
    public void add(Button button, boolean other) {
        add(button, other ? getMenu().getBackground() : null);
    }

}
