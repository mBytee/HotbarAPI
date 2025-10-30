package com.mbyte.spigotutils.utils;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Set;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public class ScannerImpl implements com.mbyte.spigotutils.api.Scanner {
    @SuppressWarnings("unchecked")
    @Override
    public Set<Class<?>> scan(ClassLoader classLoader, String packageName, Class<?> type) {
        final Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packageName)
                .setUrls(ClasspathHelper.forPackage(packageName, classLoader))
                .filterInputsBy(new FilterBuilder().includePackage(packageName)));

        return (Set<Class<?>>) reflections.getSubTypesOf(type);
    }
}
