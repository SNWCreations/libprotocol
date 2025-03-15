package snw.lib.protocol.serial;

import org.jetbrains.annotations.Nullable;
import snw.lib.protocol.handler.PacketHandler;
import snw.lib.protocol.packet.Packet;

/**
 * Represents a container of a deserialized packet.
 *
 * @param type   The packet type
 * @param nonce  The nonce
 * @param packet The deserialized packet, null if a suitable deserializer was not found
 * @param <T>    See {@link Packet}
 * @author SNWCreations
 * @since 1.0.0
 */
public record DeserializedPacket<T extends PacketHandler>(
        String type,
        String nonce,
        @Nullable Packet<T> packet
) {
}
