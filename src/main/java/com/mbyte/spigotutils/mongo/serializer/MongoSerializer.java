package com.mbyte.spigotutils.mongo.serializer;

import org.bson.BsonBinaryReader;
import org.bson.BsonBinaryWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.io.BasicOutputBuffer;

import java.nio.ByteBuffer;

/**
 * This Project is property of Codenix © 2025
 * Redistribution of this Project is not allowed
 *
 * @author mBytee
 * Created: 29/10/2025
 * Project: SpigotUtils
 */
public class MongoSerializer {

    /** The document of {@linkplain Document} documents */
    private static final Codec<Document> DOCUMENT_CODEC = new DocumentCodec();

    /**
     * Use a new {@link MongoDocument} to serialize a {@link MongoSerializable} object
     *
     * @param serializable The object to serialize
     * @return This {@link MongoDocument} instance
     */
    public static MongoDocument serialize(MongoSerializable serializable) {
        final MongoDocument document = new MongoDocument();

        serializable.save(document);

        return document;
    }

    /**
     * Serialize a document to a byte array
     *
     * @param document The document to serialize
     * @return A byte array
     */
    public static byte[] serialize(Document document) {
        final BasicOutputBuffer outputBuffer = new BasicOutputBuffer();
        final BsonBinaryWriter writer = new BsonBinaryWriter(outputBuffer);

        DOCUMENT_CODEC.encode(writer, document, EncoderContext.builder().isEncodingCollectibleDocument(true).build());

        return outputBuffer.toByteArray();
    }

    /**
     * Deserialize a document from a byte array
     *
     * @param bytes The byte array to deserialize
     * @return The deserialized {@linkplain Document document}
     */
    public static Document deserialize(byte[] bytes) {
        final BsonBinaryReader bsonReader = new BsonBinaryReader(ByteBuffer.wrap(bytes));

        return DOCUMENT_CODEC.decode(bsonReader, DecoderContext.builder().build());
    }

}
