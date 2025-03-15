package snw.lib.protocol.serial;

import com.google.common.io.ByteArrayDataInput;
import snw.lib.protocol.handler.PacketHandler;
import snw.lib.protocol.packet.Packet;

import java.util.function.Function;

/**
 * A {@link Function}-like interface used to represent a {@link Packet} deserializer.
 *
 * @param <T> See {@link Packet}
 * @author SNWCreations
 * @since 1.0.0
 */
public interface PacketDeserializer<T extends PacketHandler> {
    /**
     * Deserialize a packet from the given input.
     *
     * @param input The raw data source
     * @return A {@link DeserializedPacket} which <b>may</b> contains the deserialized packet
     */
    DeserializedPacket<T> deserialize(ByteArrayDataInput input);
}
