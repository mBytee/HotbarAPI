package com.ricodev.spigotutils.menu.task;

import com.ricodev.spigotutils.api.AutoUpdate;
import com.ricodev.spigotutils.menu.Menu;
import com.ricodev.spigotutils.menu.MenuHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@RequiredArgsConstructor
public class AutoUpdateTask implements Runnable {
    /**
     * The menu handler.
     */
    private final MenuHandler handler;

    /**
     * The tick time since the task started.
     */
    @Getter
    private long ticks = 0;

    /**
     * Loops through all open menus that is annotated with
     * AutoUpdate and updates them if the ticks since the
     * last update matches the value of the annotation.
     */
    @Override
    public void run() {
        for (Menu menu : handler.getOpenMenus().values()) {
            AutoUpdate annotation = menu.getClass().getAnnotation(AutoUpdate.class);

            if (annotation != null && ticks - menu.getLastUpdate() >= annotation.value()) {
                menu.update();
            }
        }

        ticks++;
    }
}
