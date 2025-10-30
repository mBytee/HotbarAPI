package com.ricodev.spigotutils.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.ricodev.spigotutils.mongo.serializer.MongoDocument;
import com.ricodev.spigotutils.mongo.serializer.MongoSerializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
@Getter
@Setter
public class MongoConnection {

    private boolean connected;

    private MongoClient client;

    private final String mongoUri;

    private final JavaPlugin plugin;

    public MongoConnection(JavaPlugin plugin, String mongoUri) {
        this.plugin = plugin;
        this.mongoUri = mongoUri;
        startConnection();
    }

    public void startConnection() {
        final ConnectionString connectionString = new ConnectionString(mongoUri);
        final MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToSocketSettings(builder -> builder.connectTimeout(2000, TimeUnit.MILLISECONDS))
                .retryWrites(true)
                .build();

        this.client = MongoClients.create(settings);
        this.connected = true;
        plugin.getLogger().info("MongoDB connection started!");
        Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
        Logger.getLogger("org.mongodb.driver.connection").setLevel(Level.OFF);
        Logger.getLogger("org.mongodb.driver.client").setLevel(Level.OFF);
    }

    public void stopConnection() {
        this.client.close();
        plugin.getLogger().info("MongoDB connection closed!");
    }

    public MongoDatabase getDatabase(String name) {
        return this.client.getDatabase(name);
    }
}
