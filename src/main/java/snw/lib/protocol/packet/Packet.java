package snw.lib.protocol.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import snw.lib.protocol.handler.PacketHandler;
import snw.lib.protocol.serial.std.StandardPacketDeserializer;

/**
 * Represents a packet. <br>
 * This class acted as the base class of all the packets. <br>
 * All packets should use a string as their type, they'll be used
 * by serialization. (Retrieving deserializer according to it,
 * serializing, etc.)
 *
 * @param <T> The {@link PacketHandler} type which could be used
 *            to process this packet
 * @author SNWCreations
 * @since 1.0.0
 */
@Getter
public abstract class Packet<T extends PacketHandler> {
    /**
     * A string without any meaning but <b>must not</b> be null. <br>
     * It <b>must</b> be kept in serialization and deserialization
     * <b>without any</b> modification. <br>
     * A possible usage of it is as the unique identifier of the packet
     * during the network session.
     *
     * @since 1.0.0
     */
    private final String nonce;

    /**
     * Creates the packet with the given nonce. <br>
     * Subclasses should override this constructor with the Java types of
     * their members as arguments.
     *
     * @param nonce The nonce
     * @since 1.0.0
     */
    protected Packet(String nonce) {
        this.nonce = nonce;
    }

    /**
     * Creates the packet by using the given raw data source. <br>
     * Subclasses should have their members assigned by reading data
     * from the given input.
     * The sequence of reading data must be same
     * as the sequence of writing data in the overridden
     * {@link #doSerialization(ByteArrayDataOutput)} method,
     * or serialization may fail or ending up with
     * unexpected incorrect values.
     *
     * @param input The raw data source
     * @since 1.0.0
     */
    protected Packet(ByteArrayDataInput input) {
        nonce = input.readUTF();
    }

    /**
     * Serialize this packet to the standard format which could be
     * accepted by {@link StandardPacketDeserializer}.
     *
     * @return The serialized packet data, ready to be used to re-create
     * this packet through the overridden {@link #Packet(ByteArrayDataInput)}
     * constructor
     * @since 1.0.0
     */
    public final byte[] serialize() {
        final ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(getType());
        output.writeUTF(nonce);
        doSerialization(output);
        return output.toByteArray();
    }

    /**
     * Get the type of this packet.
     *
     * @return The type string
     * @since 1.0.0
     */
    public abstract String getType();

    /**
     * Do the actual serialization. <br>
     * Subclasses should write their data to the given output in this method. <br>
     * The sequence of writing data must be same as the sequence of reading data
     * in the overridden {@link #Packet(ByteArrayDataInput)} constructor,
     * or deserialization may fail or ending up with
     * unexpected incorrect values.
     *
     * @param output The data output
     * @since 1.0.0
     */
    protected abstract void doSerialization(ByteArrayDataOutput output);

    /**
     * Handle this packet using the given handler.
     *
     * @param handler The handler
     * @since 1.0.0
     */
    public abstract void handle(T handler);
}
