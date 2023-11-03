package server;

import org.jetbrains.annotations.NotNull;
import util.OuijaBoardUtil;
import util.PacketType;

import java.io.DataOutputStream;
import java.util.Set;


public final class Transmitter {
    private final DataOutputStream out;
    private final String username;

    public Transmitter(DataOutputStream out, String username) {
        this.out = out;
        this.username = username;
    }

    public void enlighten(String senderUsername, String message) {
        // [type] [length|username] [length|message]
        OuijaBoardUtil.logClientboundPacket("[" + senderUsername + " -> " + username + "] " + message);
        OuijaBoardUtil.writePacketType(out, PacketType.ENLIGHTEN);
        OuijaBoardUtil.writeString(out, senderUsername);
        OuijaBoardUtil.writeString(out, message);
    }

    public void proclaim(String senderUsername, String message) {
        // [type] [length|username] [length|message]
        OuijaBoardUtil.logClientboundPacket("[" + senderUsername + " => " + username + "] " + message);
        OuijaBoardUtil.writePacketType(out, PacketType.PROCLAIM);
        OuijaBoardUtil.writeString(out, senderUsername);
        OuijaBoardUtil.writeString(out, message);
    }

    public void gone(String targetUsername) {
        // [type]
        OuijaBoardUtil.logClientboundPacket(username + " attempted to enlighten a nonexistant user '" + targetUsername + "'");
        OuijaBoardUtil.writePacketType(out, PacketType.GONE);
    }

    public void victims(@NotNull Set<String> victims) {
        // [type] [length] [length|username]...
        OuijaBoardUtil.logClientboundPacket("Sending victim list to " + username);
        OuijaBoardUtil.writePacketType(out, PacketType.VICTIMS);
        OuijaBoardUtil.writeInt(out, victims.size());
        for (String victim : victims) {
            OuijaBoardUtil.writeString(out, victim);
        }
    }

    public void hola(String joinedUsername) {
        // [type] [length|username]
        OuijaBoardUtil.logClientboundPacket("Informing " + username + " that " + joinedUsername + " has connected");
        OuijaBoardUtil.writePacketType(out, PacketType.HOLA);
        OuijaBoardUtil.writeString(out, joinedUsername);
    }

    public void adios(String leftUsername) {
        // [type] [length|username]
        OuijaBoardUtil.logClientboundPacket("Informing " + username + " that " + leftUsername + " has disconnected");
        OuijaBoardUtil.writePacketType(out, PacketType.ADIOS);
        OuijaBoardUtil.writeString(out, leftUsername);
    }
}
