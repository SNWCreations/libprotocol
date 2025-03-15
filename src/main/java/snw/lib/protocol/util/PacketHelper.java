package snw.lib.protocol.util;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import org.jetbrains.annotations.Nullable;
import snw.lib.protocol.io.PacketReader;
import snw.lib.protocol.io.PacketReaders;
import snw.lib.protocol.io.PacketWriter;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.IntFunction;

/**
 * The utility class which provides some useful methods to deal with the packets.
 *
 * @author SNWCreations
 * @since 1.0.0
 */
public final class PacketHelper {

    /**
     * Create a random nonce string, use {@link UUID#randomUUID()} as the random source.
     *
     * @return A newly created random nonce string
     * @since 1.0.0
     */
    public static String newNonce() {
        return UUID.randomUUID().toString();
    }

    /**
     * Write the given enum constant to the output. <br>
     * This method will write the ordinal of the given enum constant to the output,
     * -1 is written when the given enum constant is {@code null}. <br>
     * This method is expected to be used with {@link PacketReaders#enumReader(Class)}
     * method when reading.
     *
     * @param output    The output
     * @param enumConst The enum constant to write, nullable
     * @since 1.0.0
     */
    public static void writeEnum(ByteArrayDataOutput output, @Nullable Enum<?> enumConst) {
        output.writeInt(enumConst == null ? -1 : enumConst.ordinal());
    }

    /**
     * Read a map from the given input.
     *
     * @param input       The input
     * @param mFactory    The factory which is used to create the returning map
     * @param keyReader   The reader which is used to read entry keys
     * @param valueReader The reader which is used to read entry values
     * @param <K>         The key type
     * @param <V>         The value type
     * @param <M>         The map type
     * @return The map containing the data retrieved from the given input
     * @since 1.0.0
     */
    public static <K, V, M extends Map<K, V>> M readMap(ByteArrayDataInput input, IntFunction<M> mFactory, PacketReader<K> keyReader, PacketReader<V> valueReader) {
        int size = input.readInt();
        M map = mFactory.apply(size);
        for (int i = 0; i < size; i++) {
            K k = keyReader.read(input);
            V v = valueReader.read(input);
            map.put(k, v);
        }
        return map;
    }

    /**
     * Write a map to the given output.
     *
     * @param output      The data output
     * @param map         The map used to read data for writing
     * @param keyWriter   The writer which is used to write keys into the output
     * @param valueWriter The writer which is used to write values into the output
     * @param <K>         The key type
     * @param <V>         The value type
     * @since 1.0.0
     */
    public static <K, V> void writeMap(ByteArrayDataOutput output, Map<K, V> map, PacketWriter<K> keyWriter, PacketWriter<V> valueWriter) {
        output.writeInt(map.size());
        map.forEach((k, v) -> {
            keyWriter.write(output, k);
            valueWriter.write(output, v);
        });
    }

    /**
     * Read the collection from the given input.
     *
     * @param input    The data input
     * @param cFactory The collection factory
     * @param reader   The reader which is used to read elements
     * @param <E>      The element type
     * @param <T>      The collection type
     * @return The collection containing the data retrieved from the given input
     * @since 1.0.0
     */
    public static <E, T extends Collection<E>> T readCollection(ByteArrayDataInput input, IntFunction<T> cFactory, PacketReader<E> reader) {
        int size = input.readInt();
        T collection = cFactory.apply(size);
        for (int i = 0; i < size; i++) {
            E e = reader.read(input);
            collection.add(e);
        }
        return collection;
    }

    /**
     * Write the collection to the given output.
     *
     * @param output     The data output
     * @param collection The collection which is used to read data for writing
     * @param writer     The writer which is used to write elements
     * @param <T>        The element type
     * @since 1.0.0
     */
    public static <T> void writeCollection(ByteArrayDataOutput output, Collection<T> collection, PacketWriter<T> writer) {
        output.writeInt(collection.size());
        for (T element : collection) {
            writer.write(output, element);
        }
    }

    /**
     * Read an object which may or may not exist in the given input.
     *
     * @param input  The data input
     * @param reader The reader which is used to read object
     * @param <T>    The object type
     * @return The requested object, or null if the object was not found
     * @since 1.0.0
     */
    public static <T> @Nullable T readNullable(ByteArrayDataInput input, PacketReader<T> reader) {
        T t;
        if (input.readBoolean()) {
            t = reader.read(input);
        } else {
            t = null;
        }
        return t;
    }

    /**
     * Write an object which may or may not be null to the given output.
     *
     * @param output The data output
     * @param value  The object to be written
     * @param writer The writer which is used to write the object into the given output
     * @param <T>    The object type
     * @since 1.0.0
     */
    public static <T> void writeNullable(ByteArrayDataOutput output, @Nullable T value, PacketWriter<T> writer) {
        boolean notNull = value != null;
        output.writeBoolean(notNull);
        if (notNull) {
            writer.write(output, value);
        }
    }

    private PacketHelper() {
    }
}
