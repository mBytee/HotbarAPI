package com.mbyte.spigotutils.api;

import java.util.Set;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public interface Scanner {

    /**
     * Get all {@link Class} matching a given type in a provided package
     *
     * @param classLoader The {@link ClassLoader} to use
     * @param packageName The package to scan
     * @param type        The type of classes to find
     * @return All the classes found
     */
    Set<Class<?>> scan(ClassLoader classLoader, String packageName, Class<?> type);

}
