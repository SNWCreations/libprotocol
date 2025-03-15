package snw.lib.protocol.io;

import com.google.common.io.ByteArrayDataOutput;

import java.util.function.Function;

/**
 * A {@link Function}-like interface used to represent
 * a writer which could be used to convert a given object
 * of type {@link T} to binary data and then write it to
 * the given output.
 *
 * @param <T> The type of the object which could be written by this reader
 * @author SNWCreations
 * @since 1.0.0
 */
public interface PacketWriter<T> {
    /**
     * Convert the given object to binary data and then write
     * it to the given output.
     *
     * @param output The data output
     * @param object The object which is going to be written
     * @since 1.0.0
     */
    void write(ByteArrayDataOutput output, T object);
}
