package com.mbyte.spigotutils.menu.template;

import com.mbyte.spigotutils.menu.layer.impl.BackgroundLayer;
import com.mbyte.spigotutils.menu.layer.impl.ForegroundLayer;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public interface Template {
    /**
     * Applies a design to a menu.
     *
     * @param background The menu's background layer.
     * @param foreground The menu's foreground layer.
     */
    void apply(BackgroundLayer background, ForegroundLayer foreground);
}
