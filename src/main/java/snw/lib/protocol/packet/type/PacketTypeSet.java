package snw.lib.protocol.packet.type;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.Nullable;
import snw.lib.protocol.handler.PacketHandler;
import snw.lib.protocol.io.PacketReader;
import snw.lib.protocol.packet.Packet;

import java.util.HashMap;
import java.util.Map;

/**
 * The set of packet types.
 *
 * @param <T> The {@link PacketHandler} type used for {@link Packet} types
 * @author SNWCreations
 * @since 1.0.0
 */
public abstract class PacketTypeSet<T extends PacketHandler> {
    /**
     * The registered deserializers.
     *
     * @since 1.0.0
     */
    protected final Map<String, PacketReader<? extends Packet<T>>> deserializers;

    /**
     * The constructor. Subclasses can register their packet types in constructor by using
     * {@link #register(String, PacketReader)} method.
     *
     * @since 1.0.0
     */
    protected PacketTypeSet() {
        this.deserializers = new HashMap<>();
    }

    /**
     * Register the given deserializer to this set, fails if the given type name was already registered. <br>
     * Dynamic registering is allowed but <b>not recommended</b>.
     *
     * @param typeName     The packet type name
     * @param deserializer The deserializer
     * @since 1.0.0
     */
    public void register(String typeName, PacketReader<? extends Packet<T>> deserializer) {
        Preconditions.checkState(!this.deserializers.containsKey(typeName),
                "Packet type %s already registered", typeName);
        this.deserializers.put(typeName, deserializer);
    }

    /**
     * Get the deserializer matching the given type name from this set.
     *
     * @param typeName The packet type name
     * @return The deserializer, null will be returned if the given type of packet is not defined in this set
     */
    public @Nullable PacketReader<? extends Packet<T>> getDeserializer(String typeName) {
        return this.deserializers.get(typeName);
    }

}
