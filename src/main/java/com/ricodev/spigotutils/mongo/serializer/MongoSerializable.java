package com.ricodev.spigotutils.mongo.serializer;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public interface MongoSerializable {

    /**
     * Write values on a {@link MongoDocument}
     *
     * @param document The {@link MongoDocument} to write
     */
    void save(MongoDocument document);

    /**
     * Read values from a {@link MongoDocument}
     *
     * @param document The {@link MongoDocument} to read
     */
    void load(MongoDocument document);

}
