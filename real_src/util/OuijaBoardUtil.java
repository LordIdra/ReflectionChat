package util;




import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public final class OuijaBoardUtil {
    private OuijaBoardUtil() {}

    private static  String getFormattedTime() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static void logServerboundPacket(String message) {
        System.out.println("[" + getFormattedTime() + "|serverbound] " + message);
    }

    public static void logClientboundPacket(String message) {
        System.out.println("[" + getFormattedTime() + "|clientbound] " + message);
    }

    public static void writeInt( DataOutputStream out, int value) {
        try {
            out.writeInt(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writePacketType( DataOutputStream out,  PacketType type) {
        writeInt(out, (byte) type.ordinal());
    }

    public static void writeString( DataOutputStream out,  String message) {
        try {
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            out.writeInt(data.length);
            out.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int readInt( DataInputStream in) {
        try {
            return in.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static  PacketType readPacketType( DataInputStream in) {
        int ordinal;
        try {
            ordinal = in.readInt();
        } catch (EOFException e) {
            return null; // Client has disconnected
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return PacketType.class.getEnumConstants()[ordinal];
    }

    public static  String readString( DataInputStream in) {
        try {
            int bytesToRead = in.readInt();
            byte[] bytes = new byte[bytesToRead];
            in.read(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
