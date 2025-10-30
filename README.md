# SpigotUtils

A utility library for Spigot plugins providing:
- Hotbar Items API 
- Inventory Menu API 
- MongoDB utilities (connection helper and simple serialization contract)

## Requirements
- Java 8
- Spigot 1.8.8+

## Installation

Using Gradle:

```gradle
repositories {
    mavenCentral()
    maven("https://maven.codenix.cc/public-repo")
}

dependencies {
    implementation "com.mbyte:SpigotUtils:1.0.0"
}
```
Using Maven:

```xml

<repositories>
    <repository>
        <id>Codenix Repo-public-repo</id>
        <name>Codenix Repository</name>
        <url>https://maven.codenix.cc/public-repo</url>
    </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.mbyte</groupId>
    <artifactId>SpigotUtils</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```


## Getting Started

### Setup in your plugin
In your main plugin class `onEnable`, initialize the handlers.

```java
public class YourPlugin extends JavaPlugin {
    private ItemManager itemManager;
    private MenuHandler menuHandler;

    @Override
    public void onEnable() {
        // Initialize Hotbar ItemManager 
        this.itemManager = new ItemManager(this);

        // Initialize MenuHandler 
        this.menuHandler = new MenuHandler(this);

        // Optionally scan and register items from a package
        itemManager.registerItems(this, "your.package.items");
    }
}
```

### Hotbar Items API
Example Usage:

```java
import jdk.nashorn.internal.objects.annotations.Getter;

public class SpawnItem extends MItem<YourPlugin> {
    public SpawnItem(YourPlugin plugin) {
        super(
                plugin,
                "spawn_item",                    // id/name
                "&aSpawn",                       // display
                Arrays.asList("&7Teleport to spawn"),
                new ItemStack(Material.COMPASS)   // base item
        );
    }
    
    @Override
    public void onLeftClick(PlayerInteractEvent event) {
        //Your logic here
    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {
        //Your logic here
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        //Your logic here
    }
}
```

Register and give items:

```java
// Register a single item class (constructor must accept YourPlugin)
itemManager.registerItem(this, SpawnItem.class);

// Give by class to a specific slot
itemManager.giveItem(player, 0, SpawnItem.class);

// Give by name 
itemManager.giveItem(player, "spawn_item");
```

### Menu API
Build rich inventory menus with foreground and background layers, buttons, and optional pagination. Menus can be run synchronously or asynchronously by annotating the class with `@Async`.

Basic menu:

```java
public class ExampleMenu extends Menu {
    public ExampleMenu(Player player) {
        super("&bExample", MenuSize.THREE_ROWS, player);
    }

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {
        // Fill background, place buttons in foreground, etc.
        // foreground.set(x, y, new Button(itemStack, click -> { ... }));
    }
}

// Open the menu
new ExampleMenu(player).open();
```

Async refresh:

```java
@Async
public class AsyncStatsMenu extends Menu { /* ... */ }
```

Paginated menus:

```java
public class UsersMenu extends PaginatedMenu {
    public UsersMenu(Player player) {
        super("&bUsers", MenuSize.SIX_ROWS, player);
    }

    @Override
    public List<Button> getEntries() {
        // return a dynamic list of buttons to paginate
        return fetchUserButtons();
    }
}
```

### MongoDB Utilities
`MongoConnection` simplifies connecting to MongoDB.

```java

MongoConnection mongo = new MongoConnection(yourPlugin, "mongodb://user:pass@host:27017/?authSource=admin");
MongoDatabase db = mongo.getDatabase("yourdb");

//Stop connection
mongo.stopConnection();

//Serializer
@RequiredArgsConstructor
public class MyObject implements MongoSerializable {

  private UUID id;
  private String name;

  @Override
  public void save(MongoDocument document) {
    document.append("id", id);
    document.append("name", name);
  }

  @Override
  public void load(MongoDocument document) {
    this.id = UUID.fromString(document.getString("id"));
    this.name = document.getString("name");
  }

}
```

## License and Credits
Property of Codenix © 2025.
Special thanks to (https://github.com/J4C0B3Y/MenuAPI) for the Menu API base.

Authored by `mBytee`.
