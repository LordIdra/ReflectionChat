import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public final class Necromancer {
    private static final String HOST = "localhost";
    private static final int PORT = 13666;
    private final Socket socket;

    public Necromancer() {
        socket = openSocket();
    }

    private static Socket openSocket() {
        try {
            return new Socket(HOST, PORT);
        } catch (UnknownHostException | ConnectException e) {
            System.err.println("Failed to connect to server");
            System.exit(1);
            return null; // stupid language
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mainloop() {
        Scanner scanner = new Scanner(System.in);
        do {
            String line = scanner.nextLine();
            send(line);
        } while (true);
    }

    private void send(String message) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String receive() {
        try {
            return new DataInputStream(socket.getInputStream()).readUTF();
        } catch (EOFException e) {
            System.err.println("Connection lost (EOF)");
            System.exit(1);
            return null; // stupid language
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
