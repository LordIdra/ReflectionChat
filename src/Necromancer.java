import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public final class Necromancer {
    private static final String HOST = "localhost";
    private static final int PORT = 13666;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    public Necromancer() {
        socket = openSocket();
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            OuijaBoard.invocation(out, line);
        } while (true);
    }
}
