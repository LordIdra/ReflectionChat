import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public final class OuijaBoard {
    enum PacketType {
        INVOCATION,
        VICTIMS,
        WHISPER,
        BROADCAST,
        BLESSING,
        EXCOMMUNICATION,
        GONE,
    }

    private OuijaBoard() {}

    private static void sendPacketType(@NotNull DataOutputStream out, @NotNull PacketType type) {
        try {
            out.write((byte) type.ordinal()); // Without casting, this takes the MSB, which is not what we want
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendContent(@NotNull DataOutputStream out, @NotNull String message) {
        try {
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            out.writeInt(data.length);
            out.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void invocation(@NotNull DataOutputStream out, @NotNull String message) {
        sendPacketType(out, PacketType.BROADCAST);
        sendContent(out, message);
    }

    // https://www.baeldung.com/java-inputstream-server-socket
    public static @Nullable String receive(@NotNull DataInputStream in) {
        try {
            int packetTypeOrdinal = in.read();
            if (packetTypeOrdinal == -1) {
                return null;
            }
            PacketType packetType = PacketType.class.getEnumConstants()[packetTypeOrdinal];
            int bytesToRead = in.readInt();
            byte[] bytes = new byte[bytesToRead];
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
