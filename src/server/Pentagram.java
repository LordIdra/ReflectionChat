package server;

import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class Pentagram {
    private static final int PORT = 13666;
    private final ServerSocket serverSocket;
    private final Map<String, Socket> usernames = new ConcurrentHashMap<>();
    private final Map<String, Transmitter> transmitters = new ConcurrentHashMap<>();

    public Pentagram() {
        serverSocket = openSocket();
    }

    private static @NotNull ServerSocket openSocket() {
        try {
            return new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mainloop() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                String username = Authenticator.authenticate(in, out, usernames.keySet());
                if (username == null) {
                    continue;
                }

                usernames.put(username, socket);
                Transmitter transmitter = new Transmitter(out, username);
                transmitters.put(username, transmitter);
                new Thread(new ConnectionHandler(in, username, usernames.keySet(), transmitters, transmitter)).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
