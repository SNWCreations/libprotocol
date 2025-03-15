package snw.lib.protocol.io;

import com.google.common.io.ByteArrayDataOutput;
import snw.lib.protocol.util.PacketHelper;

import java.util.UUID;

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

    private PacketWriters() {
    }
}
