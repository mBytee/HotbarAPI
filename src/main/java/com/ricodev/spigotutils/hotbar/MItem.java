package com.ricodev.spigotutils.hotbar;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@Getter
public class MItem<T extends JavaPlugin> {

    /**
     * Spigot plugin
     */
    protected final T plugin;
    /**
     * Item name/id. Example: my_item
     */
    protected final String name;
    /**
     * The display of the item
     */
    protected String display;
    /**
     * The description of the item
     */
    protected List<String> description;
    /**
     * The bukkit item
     */
    protected ItemStack item;

    /**
     * Constructor of a {@link MItem}
     *
     * @param plugin      The plugin instance
     * @param name        The name of the item
     * @param display     The display of the item
     * @param description The description of the item
     * @param item        The bukkit item
     */
    public MItem(T plugin, String name, String display, List<String> description, ItemStack item) {
        this.plugin = plugin;
        this.name = name;
        this.display = display;
        this.description = description;
        this.item = item;
    }

    /**
     * Constructor of a {@link MItem}
     *
     * @param plugin      The plugin instance
     * @param name        The name of the item
     * @param display     The display of the item
     * @param description The description of the item
     * @param material    The material of the bukkit item
     */
    public MItem(T plugin, String name, String display, List<String> description, Material material) {
        this(plugin, name, display, description, new ItemStack(material));
    }

    /**
     * Called before giving the item to the player
     *
     * @param player    The player that will receive the item
     * @param slot      The slot where the item will be set
     * @param itemStack The item stack that will be set
     * @return A modified {@link ItemStack} or by default, the auto-created {@link ItemStack}
     */
    public ItemStack onPreGive(Player player, int slot, ItemStack itemStack) {
        return itemStack;
    }

    /**
     * Called when this item is given to a player
     *
     * @param player    Player with the item
     * @param slot      Slot
     * @param itemStack {@link ItemStack}
     */
    public void onGive(Player player, int slot, ItemStack itemStack) {
    }

    /**
     * Called when a player left click with this item
     *
     * @param event  Event fired
     */
    public void onLeftClick(PlayerInteractEvent event) {
    }

    /**
     * Called when a player right click with this item
     *
     * @param event  Event fired
     */
    public void onRightClick(PlayerInteractEvent event) {
    }

    /**
     * Called when a player click on the item in his inventory
     *
     * @param event  The event fired
     */
    public void onInventoryClick(InventoryClickEvent event) {
    }

}
