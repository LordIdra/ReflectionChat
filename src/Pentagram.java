import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public final class Pentagram {
    private static final int PORT = 13666;
    private final ServerSocket socket;
    private final Socket client;
    private final DataInputStream in;
    private final DataOutputStream out;

    public Pentagram() {
        try {
            socket = openSocket();
            client = socket.accept();
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mainloop() {
        String received = "";
        while (true) {
            if (client.isClosed()) {
                System.out.println("Client disconnected");
                return;
            }
            received = OuijaBoard.receive(in);
            if (received == null) {
                break;
            }
            System.out.println(received);
        }
    }

    private static @NotNull ServerSocket openSocket() {
        try {
            return new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
