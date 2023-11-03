package server;

import util.OuijaBoardUtil;
import util.PacketType;

import java.io.DataInputStream;
import java.util.Map;
import java.util.Set;


public class ConnectionHandler implements Runnable {
    private final DataInputStream in;
    private final String username;
    private final Set<String> usernames;
    private final Map<String, Transmitter> transmitters;
    private final Transmitter transmitter;

    public ConnectionHandler(DataInputStream in, String username, Set<String> usernames, Map<String, Transmitter> transmitters, Transmitter transmitter) {
        this.in = in;
        this.username = username;
        this.usernames = usernames;
        this.transmitter = transmitter;
        this.transmitters = transmitters;
    }

    private void victims() {
        transmitter.victims(usernames);
    }

    private void enlighten() {
        String targetUsername = OuijaBoardUtil.readString(in);
        String message = OuijaBoardUtil.readString(in);
        if (!usernames.contains(targetUsername)) {
            transmitter.gone(targetUsername);
        }
        transmitters.get(targetUsername).enlighten(username, message);
    }

    private void proclaim() {
        String message = OuijaBoardUtil.readString(in);
        transmitters.values().forEach(transmitter -> transmitter.proclaim(username, message));
    }

    @Override
    public void run() {
        transmitters.values().forEach(transmitter -> transmitter.hola(username));

        boolean connected = true;
        while (connected) {
            PacketType packetType = OuijaBoardUtil.readPacketType(in);
            if (packetType == null) {
                break; // Client has disconnected
            }

            switch (packetType) {
                case VICTIMS -> victims();
                case ENLIGHTEN -> enlighten();
                case PROCLAIM -> proclaim();
                default -> {
                    OuijaBoardUtil.logServerboundPacket("Received illegal packet " + packetType.name() + " from " + username + "; disconnecting them");
                    connected = false;
                }
            }
        }

        transmitters.values().stream()
                .filter(transmitter1 -> transmitter1 != transmitter) // Don't try and send messages to the client that just disconnected
                .forEach(transmitter -> transmitter.adios(username));
    }
}
