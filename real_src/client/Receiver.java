package client;

import util.OuijaBoardUtil;
import util.PacketType;

import java.io.DataInputStream;


public class Receiver implements Runnable {
    private final Necromancer necromancer;
    private final DataInputStream in;

    public Receiver(Necromancer necromancer, DataInputStream in) {
        super();
        this.necromancer = necromancer;
        this.in = in;
    }

    private void enlighten() {
        String username = OuijaBoardUtil.readString(in);
        String message = OuijaBoardUtil.readString(in);
        System.out.println("[" + username + " -> you] " + message);
    }

    private void proclaim() {
        String username = OuijaBoardUtil.readString(in);
        String message = OuijaBoardUtil.readString(in);
        System.out.println("[" + username + "] " + message);
    }

    private static void gone() {
        System.out.println("That user does not exist");
    }

    private void victims() {
        int victimCount = OuijaBoardUtil.readInt(in);
        System.out.print("Connected victims: ");
        for (int i = 0; i < victimCount; i++) {
            System.out.print(OuijaBoardUtil.readString(in));
            if (i != victimCount - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    private void hola() {
        System.out.println("[+] " + OuijaBoardUtil.readString(in) + " joined the Pentagram");
    }

    private void adios() {
        System.out.println("[-] " + OuijaBoardUtil.readString(in) + " left the Pentagram");
    }

    @Override
    public void run() {
        while (true) {
            PacketType packetType = OuijaBoardUtil.readPacketType(in);
            if (packetType == null) {
                System.out.println("Connection lost");
                necromancer.end();
                return;
            }

            switch (packetType) {
                case ENLIGHTEN -> enlighten();
                case PROCLAIM -> proclaim();
                case GONE -> gone();
                case VICTIMS -> victims();
                case HOLA -> hola();
                case ADIOS -> adios();
                default -> {
                    System.out.println("Received illegal packet " + packetType.name() + "; terminating connection");
                    necromancer.end();
                }
            }
        }
    }
}
