import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public final class Necromancer {
    private static final String HOST = "localhost";
    private static final int PORT = 13666;

    private Necromancer() {}

    private static Socket openSocket() {
        try {
            return new Socket(HOST, PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String waitForInput(Socket socket) {
        try {
            return new DataInputStream(socket.getInputStream()).readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendMessage(Socket socket, String message) {
        try {
            new DataOutputStream(socket.getOutputStream()).writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void summonDevil() {
        Socket clientSocket = openSocket();
        sendMessage(clientSocket, "HELLO!");
//        do {
//            System.out.println(waitForInput(clientSocket));
//        } while (true);
    }
}
