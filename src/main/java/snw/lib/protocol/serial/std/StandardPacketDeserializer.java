package snw.lib.protocol.serial.std;

import com.google.common.io.ByteArrayDataInput;
import snw.lib.protocol.handler.PacketHandler;
import snw.lib.protocol.io.PacketReader;
import snw.lib.protocol.packet.Packet;
import snw.lib.protocol.packet.type.PacketTypeSet;
import snw.lib.protocol.serial.DeserializedPacket;
import snw.lib.protocol.serial.PacketDeserializer;

/**
 * The standard implementation of packet deserializer.
 *
 * @param <T> See {@link PacketDeserializer}
 * @author SNWCreations
 * @since 1.0.0
 */
public final class StandardPacketDeserializer<T extends PacketHandler> implements PacketDeserializer<T> {
    private final PacketTypeSet<T> packetTypeSet;

    public StandardPacketDeserializer(PacketTypeSet<T> packetTypeSet) {
        this.packetTypeSet = packetTypeSet;
    }

    @Override
    public DeserializedPacket<T> deserialize(ByteArrayDataInput input) {
        final String type = input.readUTF();
        final String nonce;
        final Packet<T> packet;
        final PacketReader<? extends Packet<T>> deserializer;
        deserializer = packetTypeSet.getDeserializer(type);
        if (deserializer != null) {
            packet = deserializer.read(input);
            nonce = packet.getNonce();
        } else {
            packet = null;
            nonce = input.readUTF();
        }
        return new DeserializedPacket<>(type, nonce, packet);
    }
}
