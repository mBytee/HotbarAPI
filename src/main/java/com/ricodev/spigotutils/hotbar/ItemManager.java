package com.ricodev.spigotutils.hotbar;

import com.ricodev.spigotutils.api.IItemManager;
import com.ricodev.spigotutils.hotbar.event.ItemGiveEvent;
import com.ricodev.spigotutils.utils.CC;
import com.ricodev.spigotutils.utils.PlayerUtil;
import com.ricodev.spigotutils.utils.Reflection;
import com.ricodev.spigotutils.utils.ScannerImpl;
import com.ricodev.spigotutils.utils.item.ItemBuilder;
import de.tr7zw.nbtapi.NBT;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@Getter
public class ItemManager implements IItemManager {

    public static final String ITEM_NBT_KEY = "SpigotUtilsItem";
    @Getter
    private static ItemManager instance;
    private final Map<String, MItem<?>> items = new HashMap<>();
    private final JavaPlugin plugin;
    @Getter
    private final ScannerImpl scanner;

    public ItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
        instance = this;
        this.scanner = new ScannerImpl();
        Bukkit.getPluginManager().registerEvents(new ItemHandler(plugin), plugin);
        plugin.getLogger().info("ItemManager initialized for" + plugin.getClass().getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void registerItems(JavaPlugin pluginProvider, String packageName) {
        final Set<Class<?>> classes = this.getScanner().scan(pluginProvider.getClass().getClassLoader(), packageName, MItem.class);
        pluginProvider.getLogger().info("Searching for items in '" + packageName + "' package");

        if (classes == null) {
            return;
        }

        for (Class<?> clazz : classes) {
            if (Reflection.hasConstructorWithParameters(clazz, pluginProvider.getClass())) {
                this.registerItem(pluginProvider, (Class<? extends MItem<?>>) clazz);
            }
        }

    }

    @Override
    public void registerItem(JavaPlugin pluginProvider, Class<? extends MItem<?>> itemClass) {
        try {
            final MItem<?> item = itemClass.getConstructor(pluginProvider.getClass()).newInstance(pluginProvider);
            final String itemId = pluginProvider.getName() + ":" + item.getName();

            this.items.put(itemId, item);

            pluginProvider.getLogger().info("Registered '" + itemId + "' item");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void giveItem(Player player, int slot, Class<? extends MItem<?>> itemClass) {
        final MItem<?> item = this.getItem(itemClass);

        if (item != null) {
            this.giveItem(player, slot, item.getName());
        }
    }

    @Override
    public void giveItem(Player player, int slot, String name) {
        if (this.isItemExisting(name)) {
            final MItem<?> item = this.items.get(this.getFullItemId(name));
            final ItemGiveEvent event = new ItemGiveEvent(player.getUniqueId(), item);

            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            final ItemStack itemStack = item.onPreGive(player, slot, this.toItemStack(player, item));

            player.getInventory().setItem(slot, itemStack);

            item.onGive(player, slot, itemStack);
        }
    }

    @Override
    public boolean giveItem(Player player, Class<? extends MItem<?>> itemClass) {
        final MItem<?> item = this.getItem(itemClass);

        if (item != null) {
            return this.giveItem(player, item.getName());
        }
        return false;
    }

    @Override
    public boolean giveItem(Player player, String name) {
        if (this.isItemExisting(name)) {
            final MItem<?> item = this.items.get(this.getFullItemId(name));
            final ItemGiveEvent event = new ItemGiveEvent(player.getUniqueId(), item);

            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return false;
            }

            final ItemStack itemStack = item.onPreGive(player, -1, this.toItemStack(player, item));

            return PlayerUtil.addItemInPlayerInventory(itemStack, player);
        }
        return false;
    }

    @Override
    public <T extends MItem<?>> T getItem(Class<T> itemClass) {
        for (MItem<?> item : this.items.values()) {
            if (item.getClass() == itemClass) {
                return itemClass.cast(item);
            }
        }
        return null;
    }

    @Override
    public MItem<?> getItem(String name) {
        return this.items.get(this.getFullItemId(name));
    }

    @Override
    public Map<String, MItem<?>> getItems() {
        return this.items;
    }

    private boolean isItemExisting(String name) {
        for (MItem<?> item : this.items.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private ItemStack toItemStack(Player player, MItem<?> item) {
        ItemStack stack = new ItemBuilder(item.getItem().clone())
                .withName(CC.translate(item.getDisplay()))
                .withLore(CC.translate(item.getDescription()))
                .build();

        NBT.modify(stack, nbt -> {
            nbt.setString(ITEM_NBT_KEY, CC.translate(item.getName()));
        });
        return stack;
    }

    private String getFullItemId(String name) {
        for (MItem<?> item : this.items.values()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return this.items.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), item)).map(Map.Entry::getKey).collect(Collectors.toList()).get(0);
            } else {
                for (String itemId : this.items.keySet()) {
                    if (itemId.equalsIgnoreCase(name)) {
                        return itemId;
                    }
                }
            }
        }
        return null;
    }
}
