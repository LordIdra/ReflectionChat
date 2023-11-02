package ouija.board;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public final class OuijaBoardUtil {
    enum PacketType {
        INVOCATION,
        VICTIMS,
        ENLIGHTEN,
        PROCLAIM,
        BLESSING,
        EXCOMMUNICATION,
        GONE,
    }

    private OuijaBoardUtil() {}

    public static void writeInt(@NotNull DataOutputStream out, int value) {
        try {
            out.writeInt(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writePacketType(@NotNull DataOutputStream out, @NotNull PacketType type) {
        try {
            out.write((byte) type.ordinal());
            System.out.println((byte) type.ordinal());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeContent(@NotNull DataOutputStream out, @NotNull String message) {
        try {
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            out.writeInt(data.length);
            out.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            System.out.println(bytesToRead);
            byte[] bytes = new byte[bytesToRead];
            in.read(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
