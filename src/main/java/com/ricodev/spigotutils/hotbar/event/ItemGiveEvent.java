package com.ricodev.spigotutils.hotbar.event;

import com.ricodev.spigotutils.hotbar.MItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@RequiredArgsConstructor
@Getter
@Setter
public class ItemGiveEvent extends Event implements Cancellable {

    private final UUID playerId;
    private final MItem<?> item;
    private boolean cancelled;

    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }
}
