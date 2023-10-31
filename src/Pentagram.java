import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;


public final class Pentagram {
    private static final int PORT = 13666;

    private Pentagram() {}

    private static @NotNull ServerSocket openSocket() {
        try {
            return new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull String waitForInput(@NotNull ServerSocket serverSocket) {
        try {
            return new DataInputStream(serverSocket.accept().getInputStream()).readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void summonDevil() {
        ServerSocket serverSocket = openSocket();
        do {
            System.out.println(waitForInput(serverSocket));
        } while (true);
    }
}
