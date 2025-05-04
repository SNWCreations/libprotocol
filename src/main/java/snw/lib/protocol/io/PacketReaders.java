package snw.lib.protocol.io;

import org.jetbrains.annotations.Nullable;
import snw.lib.protocol.util.PacketHelper;

import java.util.Optional;
import java.util.UUID;

import static snw.lib.protocol.util.PacketHelper.readNullable;

/**
 * The utility class which provides some {@link PacketReader}s.
 *
 * @author SNWCreations
 * @since 1.0.0
 */
public final class PacketReaders {
    /**
     * The {@link PacketReader} which could be used to read an {@link UUID} from an input.
     *
     * @since 1.0.0
     */
    public static final PacketReader<UUID> UUID;

    static {
        UUID = input -> {
            long most = input.readLong();
            long least = input.readLong();
            return new UUID(most, least);
        };
    }

    /**
     * Creates a {@link PacketReader} which could be used to read an enum constant
     * from the given enum class.
     *
     * @param clazz An enum class
     * @param <E>   The enum type
     * @return The requested {@link PacketReader}
     * @since 1.0.0
     */
    public static <E> PacketReader<@Nullable E> enumReader(Class<E> clazz) {
        return input -> {
            int ordinal = input.readInt();
            if (ordinal == -1) {
                return null;
            }
            return clazz.getEnumConstants()[ordinal];
        };
    }

    /**
     * Create a {@link PacketReader} which could be used to read {@link Optional}-wrapped {@link E}. <br>
     * This is a {@link PacketReader}-based wrapper of {@link PacketHelper#readNullable}.
     *
     * @param reader The reader which is used to read actual value
     * @return The requested {@link PacketReader}
     * @param <E> The type of actual value being read
     * @since 1.1.0
     */
    public static <E> PacketReader<Optional<E>> optionalReader(PacketReader<E> reader) {
        return input -> Optional.ofNullable(readNullable(input, reader));
    }

    private PacketReaders() {
    }
}
