package ouija.board;

import org.jetbrains.annotations.NotNull;
import ouija.board.OuijaBoardUtil.PacketType;

import java.io.DataOutputStream;


public class OuijaBoardClient {
    private OuijaBoardClient() {}

    public static void sendInvocation(@NotNull DataOutputStream out, @NotNull String username) {
        // [type] [length|username]
        OuijaBoardUtil.writePacketType(out, PacketType.INVOCATION);
        OuijaBoardUtil.writeContent(out, username);
    }

    public static void sendVictims(@NotNull DataOutputStream out) {
        // [type]
        OuijaBoardUtil.writePacketType(out, PacketType.VICTIMS);
    }

    public static void sendEnlighten(@NotNull DataOutputStream out, @NotNull String username, @NotNull String message) {
        // [type] [length|username] [length|message]
        OuijaBoardUtil.writePacketType(out, PacketType.ENLIGHTEN);
        OuijaBoardUtil.writeContent(out, username);
        OuijaBoardUtil.writeContent(out, message);
    }

    public static void sendProclaim(@NotNull DataOutputStream out, @NotNull String message) {
        // [type] [length|message]
        OuijaBoardUtil.writePacketType(out, OuijaBoardUtil.PacketType.PROCLAIM);
        OuijaBoardUtil.writeContent(out, message);
    }
}
