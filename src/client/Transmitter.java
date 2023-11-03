package client;

import util.OuijaBoardUtil;
import util.PacketType;

import java.io.DataOutputStream;
import java.util.Scanner;


public class Transmitter implements Runnable {
    private final Scanner scanner;
    private final DataOutputStream out;

    public Transmitter(Scanner scanner, DataOutputStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    private void victims() {
        // [type]
        OuijaBoardUtil.writePacketType(out, PacketType.VICTIMS);
    }

    private void enlighten(String victim, String message) {
        // [type] [length|username] [length|message]
        OuijaBoardUtil.writePacketType(out, PacketType.ENLIGHTEN);
        OuijaBoardUtil.writeString(out, victim);
        OuijaBoardUtil.writeString(out, message);
    }

    private void proclaim(String message) {
        // [type] [length|message]
        OuijaBoardUtil.writePacketType(out, PacketType.PROCLAIM);
        OuijaBoardUtil.writeString(out, message);
    }

    @Override
    public void run() {
        while (true) {
            String input = scanner.nextLine();

            if (input.startsWith("/victims")) {
                victims();
                // User's message will be displayed since the server will just send the message to everyone connected (including sender)
                continue;
            }

            if (input.startsWith("/enlighten")) {
                // eg: /enlighten Dave Hello, I saw your blog post, I think it was kinda stupid ngl.
                String parameters = input.substring(11);
                String user = parameters.substring(0, parameters.indexOf(' '));
                String message = parameters.substring(parameters.indexOf(' ') + 1);
                enlighten(user, message);
                System.out.println("[you -> " + user + "] " + message);
                continue;
            }

            proclaim(input);
        }
    }
}
