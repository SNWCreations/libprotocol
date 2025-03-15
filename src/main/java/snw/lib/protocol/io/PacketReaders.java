package snw.lib.protocol.io;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

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

    private PacketReaders() {
    }
}
