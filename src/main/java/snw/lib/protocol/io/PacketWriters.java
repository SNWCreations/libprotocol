package snw.lib.protocol.io;

import com.google.common.io.ByteArrayDataOutput;
import snw.lib.protocol.util.PacketHelper;

import java.util.Optional;
import java.util.UUID;

import static snw.lib.protocol.util.PacketHelper.writeNullable;

/**
 * The utility class which provides some {@link PacketWriter}s. <br>
 * To write enum values, use {@link PacketHelper#writeEnum(ByteArrayDataOutput, Enum)}.
 *
 * @author SNWCreations
 * @since 1.0.0
 */
public final class PacketWriters {
    /**
     * The {@link PacketWriter} which could be used to write an {@link UUID} to an output.
     *
     * @since 1.0.0
     */
    public static final PacketWriter<UUID> UUID;

    static {
        UUID = (output, uuid) -> {
            long most = uuid.getMostSignificantBits();
            long least = uuid.getLeastSignificantBits();
            output.writeLong(most);
            output.writeLong(least);
        };
    }

    /**
     * Create a {@link PacketWriter} which could be used to write an {@link Optional}-wrapped {@link E} to an output. <br>
     * This is a {@link PacketWriter}-based wrapper of {@link PacketHelper#writeNullable}.
     *
     * @param writer The writer which is used to write the actual value to an output
     * @return The requested {@link PacketWriter}
     * @param <E> The type of actual value being written
     * @since 1.1.0
     */
    public static <E> PacketWriter<Optional<E>> optionalWriter(PacketWriter<E> writer) {
        return (output, optional) -> writeNullable(output, optional.orElse(null), writer);
    }

    private PacketWriters() {
    }
}
