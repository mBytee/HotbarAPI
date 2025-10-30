package com.ricodev.spigotutils.menu;

import com.ricodev.spigotutils.api.Async;
import com.ricodev.spigotutils.menu.button.Button;
import com.ricodev.spigotutils.menu.button.ButtonClick;
import com.ricodev.spigotutils.menu.layer.impl.BackgroundLayer;
import com.ricodev.spigotutils.menu.layer.impl.ForegroundLayer;
import com.ricodev.spigotutils.menu.template.Template;
import com.ricodev.spigotutils.menu.utils.Position;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@Getter
public abstract class Menu {
    /**
     * The in game chest inventory width.
     */
    public static final int COLUMNS = 9;

    /**
     * The menu handler the menu should use.
     */
    private final MenuHandler handler;

    /**
     * The buttons shown inside the inventory.
     */
    private final Map<Integer, Button> buttons = new HashMap<>();

    /**
     * The player associated with the menu.
     */
    private final Player player;
    /**
     * The menu size in rows.
     */
    private final int rows;
    /**
     * The menu's foreground layer.
     */
    private final ForegroundLayer foreground = new ForegroundLayer(this);
    /**
     * The menu's background layer.
     */
    private final BackgroundLayer background = new BackgroundLayer(this);
    /**
     * If the menu should be asynchronous.
     */
    private final boolean async;
    /**
     * The underlying bukkit inventory the player sees.
     */
    private Inventory inventory;
    /**
     * The title shown at the top of the inventory.
     */
    private String title;
    /**
     * The previous menu, if present.
     */
    @Setter
    private Menu previousMenu;
    /**
     * The last tick that the menu was updated.
     */
    private long lastUpdate = 0;

    /**
     * If the menu's buttons have been set up.
     */
    private boolean setup = false;

    /**
     * Creates a new menu, initializing the underlying bukkit inventory.
     *
     * @param title   The title, auto translated.
     * @param size    The menu size, in rows.
     * @param player  The player to open the menu with.
     * @param handler The handler to open the menu with.
     */
    public Menu(String title, MenuSize size, Player player, MenuHandler handler) {
        this.title = title;
        this.rows = size.getRows();
        this.player = player;
        this.handler = handler;

        if (this.handler == null) {
            throw new IllegalStateException("No menu handler instance found.");
        }

        this.inventory = createInventory();
        this.async = getClass().isAnnotationPresent(Async.class);
    }

    /**
     * Creates a new menu, initializing the underlying bukkit inventory.
     *
     * @param title  The title, auto translated.
     * @param size   The menu size, in rows.
     * @param player The player to open the menu with.
     */
    public Menu(String title, MenuSize size, Player player) {
        this(title, size, player, MenuHandler.getInstance());
    }

    private Inventory createInventory() {
        return handler.getInventoryCreator().apply(player, getTotalSlots(), this.title);
    }

    /**
     * Used for setting up the menu layout,
     * called when the inventory is first opened.
     *
     * @param background The background layer.
     * @param foreground The foreground layer.
     */
    public abstract void setup(BackgroundLayer background, ForegroundLayer foreground);

    /**
     * Called when the menu opens.
     */
    public void onOpen() {
    }

    /**
     * Called when the menu closes.
     */
    public void onClose() {
    }

    /**
     * Opens the menu, closing any existing menus if they are open.
     * The setup method is called on the first invocation of this method.
     */
    public void open() {
        // This will schedule at most 1 task using the bukkit scheduler.
        // Usually none, except when the menu is async or the menu is
        // sync and opened not on the server thread.
        handler.schedule(() -> {
            if (!setup) {
                setup(background, foreground);
                setup = true;
            }

            refresh();

            handler.schedule(() -> {
                Menu existing = handler.getOpenMenus().get(player);

                if (existing == null) {
                    player.closeInventory();
                } else {
                    existing.close(handler.isResetCursor());
                }

                player.openInventory(inventory);
                handler.getOpenMenus().put(player, this);
                handler.run(this::onOpen, async);
            });
        }, async);
    }

    /**
     * Updates the inventory's title while open.
     *
     * @param title The new title.
     */
    public void setTitle(String title) {
        this.title = title;

        // Create an inventory with the new title.
        Inventory inventory = createInventory();

        // Copy the contents of the old inventory
        inventory.setContents(this.inventory.getContents());
        this.inventory = inventory;

        handler.ifOpen(player, menu -> {
            if (menu != this) return;

            // Open the new inventory for the player.
            handler.schedule(() ->
                    player.openInventory(this.inventory)
            );
        });
    }

    /**
     * Removes the player from the open menus map.
     *
     * @param exit If the player's inventory should be closed.
     */
    public void close(boolean exit) {
        if (exit) {
            player.closeInventory();
        }

        handler.getOpenMenus().remove(player, this);
        handler.run(this::onClose, async);
    }

    /**
     * Closes the menu and removes the player from the open menus map.
     */
    public void close() {
        close(true);
    }

    /**
     * Merges layers and updates the inventory contents.
     */
    protected void refresh() {
        lastUpdate = handler.getAutoUpdateTask().getTicks();
        buttons.clear();

        ItemStack[] icons = new ItemStack[getTotalSlots()];
        buttons.putAll(foreground.getButtons(icons));
        buttons.putAll(background.getButtons(icons));

        handler.schedule(() -> {
            inventory.setContents(icons);
            player.updateInventory();
        });
    }

    /**
     * Updates the inventory buttons shown in the menu.
     */
    public void update() {
        handler.schedule(this::refresh, async);
    }

    /**
     * Returns to the previous menu, if the handler
     * has closeOnBack set and there is no previous
     * menu, then the menu will close.
     */
    public void back() {
        if (previousMenu != null) {
            previousMenu.open();
            return;
        }

        if (handler.isCloseOnBack()) {
            close();
        }
    }

    /**
     * Applies a template to the background and foreground layers.
     *
     * @param template The template to apply.
     */
    public void apply(Template template) {
        template.apply(background, foreground);
    }

    /**
     * Handles a menu click, sending the click to the slot's button.
     *
     * @param event The inventory click event.
     */
    public void click(InventoryClickEvent event) {
        handler.schedule(() -> {
            Button button = buttons.get(event.getRawSlot());
            if (button == null) return;

            button.onClick(new ButtonClick(event, button, this));
        }, async);
    }

    /**
     * Checks if there is a previous menu set.
     *
     * @return If there is a previous menu.
     */
    public boolean hasPreviousMenu() {
        return previousMenu != null;
    }

    /**
     * Gets the maximum slots in the menu.
     *
     * @return The total slots.
     */
    public int getTotalSlots() {
        return rows * COLUMNS;
    }

    /**
     * Converts x and y coordinates from the
     * top left of the menu into an index.
     *
     * @param x The x coordinate.
     * @param y The y coordinate
     * @return The index of the coordinates.
     */
    public int getIndex(int x, int y) {
        return y * COLUMNS + x;
    }

    /**
     * Converts an index into x and y
     * coordinates from the top left of the menu.
     *
     * @param index The index of the slot.
     * @return The x and y coordinates.
     */
    public Position getPosition(int index) {
        return new Position(index % COLUMNS, index / COLUMNS);
    }
}
