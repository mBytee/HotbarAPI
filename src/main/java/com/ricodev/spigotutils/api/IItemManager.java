package com.ricodev.spigotutils.api;

import com.ricodev.spigotutils.hotbar.MItem;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public interface IItemManager {


    /**
     * Register all items from a given provider in a given package
     *
     * @param pluginProvider {@link JavaPlugin} object
     * @param packageName    Package to search
     */
    void registerItems(JavaPlugin pluginProvider, String packageName);

    /**
     * Register a given item class
     *
     * @param pluginProvider {@link JavaPlugin} object
     * @param itemClass      Item's class
     */
    void registerItem(JavaPlugin pluginProvider, Class<? extends MItem<?>> itemClass);

    /**
     * Give an item to a player
     *
     * @param player    Player
     * @param slot      Item's slot
     * @param itemClass Item's class
     */
    void giveItem(Player player, int slot, Class<? extends MItem<?>> itemClass);

    /**
     * Give an item to a player
     *
     * @param player Player
     * @param slot   Item's slot
     * @param name   Item's name
     */
    void giveItem(Player player, int slot, String name);

    /**
     * Give an item to a player
     *
     * @param player    Player
     * @param itemClass Item's class
     * @return <code>true</code> if an item with the given class has been found
     */
    boolean giveItem(Player player, Class<? extends MItem<?>> itemClass);

    /**
     * Give an item to a player
     *
     * @param player Player
     * @param name   Item's name
     * @return <code>true</code> if an item with the given name has been found
     */
    boolean giveItem(Player player, String name);

    /**
     * Get an item by its class
     *
     * @param itemClass The class of the item to get
     * @param <T>       The type of the item
     * @return The item
     */
    <T extends MItem<?>> T getItem(Class<T> itemClass);

    /**
     * Get an item by its name
     *
     * @param name Item's name
     * @return {@link MItem} object
     */
    MItem<?> getItem(String name);

    /**
     * Get all items
     *
     * @return A map of items. Key: item's id, Value: the item
     */
    Map<String, MItem<?>> getItems();

}
