package server;



import util.OuijaBoardUtil;
import util.PacketType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Set;


public final class Authenticator {
    private Authenticator() {}

    private static void excommunication(DataOutputStream out) {
        // [type]
        OuijaBoardUtil.logClientboundPacket("Crystal ball does not approve of this user. Proceeding to excommunicate them from the Pentagram.");
        OuijaBoardUtil.writePacketType(out, PacketType.EXCOMMUNICATION);
    }

    private static void blessing(DataOutputStream out) {
        // [type]
        OuijaBoardUtil.logClientboundPacket("Crystal ball approves of this user. Proceeding to give them divine blessing and welcome them to the Pentagram.");
        OuijaBoardUtil.writePacketType(out, PacketType.BLESSING);
    }

    public static  String authenticate(DataInputStream in, DataOutputStream out,  Set<String> usernames) {
        PacketType packetType = OuijaBoardUtil.readPacketType(in);
        if (packetType != PacketType.INVOCATION) {
            String packetName = packetType == null ? "NULL" : packetType.name();
            OuijaBoardUtil.logServerboundPacket("Received illegal packet " + packetName + " while waiting for authentication; disconnecting client");
            return null;
        }

        String username = OuijaBoardUtil.readString(in);
        OuijaBoardUtil.logServerboundPacket("Invocation received with username '" + username + "' - consulting crystal ball...");

        // If username already exists, excommunicate the user
        if (usernames.contains(username)) {
            excommunication(out);
            return null;
        }

        blessing(out);
        return username;
    }
}
