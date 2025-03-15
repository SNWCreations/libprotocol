package snw.lib.protocol.io;

import com.google.common.io.ByteArrayDataInput;

import java.util.function.Function;

/**
 * A {@link Function}-like interface used to represent
 * a reader which could be used to read raw data from
 * the given input and then convert it to the Java equivalent
 * of {@link T}.
 *
 * @param <T> The type of the output object
 * @author SNWCreations
 * @since 1.0.0
 */
public interface PacketReader<T> {
    /**
     * Read the data from the given input and convert it
     * to the Java equivalent of {@link T}.
     *
     * @param input The data source
     * @return An object of type {@link T}
     * @since 1.0.0
     */
    T read(ByteArrayDataInput input);
}
