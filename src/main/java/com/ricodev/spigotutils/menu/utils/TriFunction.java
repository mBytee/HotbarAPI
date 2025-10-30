package com.ricodev.spigotutils.menu.utils;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@FunctionalInterface
public interface TriFunction<F, S, T, R> {
    R apply(F first, S second, T third);
}
