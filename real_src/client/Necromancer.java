package client;




import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


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

    private static  Socket openSocket() {
        try {
            return new Socket(HOST, PORT);
        } catch (UnknownHostException | ConnectException e) {
            throw new RuntimeException("Failed to connect to server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private  String authenticate( Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (!Authenticator.authenticate(in, out, username)) {
            System.out.println("You have been excommunicated");
            return null;
        }
        System.out.println("Authentication successful; welcome to the Pentagram!");
        System.out.println();
        return username;
    }

    public void mainloop() {
        Scanner scanner = new Scanner(System.in);
        String username = authenticate(scanner);
        if (username == null) {
            return;
        }

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        //private ScheduledFuture<?> stayUndeadFuture;
        Transmitter transmitter = new Transmitter(scanner, out);
        Receiver receiver = new Receiver(this, in);
        executor.submit(transmitter);
        executor.submit(receiver);
        //stayUndeadFuture = executor.scheduleAtFixedRate(new StayUndeadManager(this, out), 0, 100, TimeUnit.MILLISECONDS);

        // If program is terminated with ctrl+c, show a disconnect message
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[-] " + username);
            end();
        }));
    }

    public void end() {
        try {
            socket.close();
        } catch (IOException ignored) {
            // Socket was probably closed already
        }
        //stayUndeadFuture.cancel(true);
        System.exit(0);
    }

    public static void run() {
        new Necromancer().mainloop();
    }
}
