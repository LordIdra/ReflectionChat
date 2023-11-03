package client;

import util.OuijaBoardUtil;
import util.PacketType;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public final class Authenticator {
    private Authenticator() {}

    private static void invocation(DataOutputStream out, String username) {
        // [type] [length|username]
        OuijaBoardUtil.writePacketType(out, PacketType.INVOCATION);
        OuijaBoardUtil.writeString(out, username);
    }

    private static boolean awaitResponse(DataInputStream in) {
        PacketType packetType = OuijaBoardUtil.readPacketType(in);
        if (packetType == null) {
            throw new RuntimeException("Pentagram sent invalid response to authentication request");
        }

        switch (packetType) {
            case EXCOMMUNICATION -> { return false; }
            case BLESSING -> { return true; }
            default -> throw new RuntimeException("Received unexpected packet while awaiting authentication");
        }
    }

    public static boolean authenticate(DataInputStream in, DataOutputStream out, String username) {
        invocation(out, username);
        return awaitResponse(in);
    }
}
