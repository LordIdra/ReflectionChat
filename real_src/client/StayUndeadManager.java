package client;

import util.OuijaBoardUtil;
import util.PacketType;

import java.io.DataOutputStream;


public class StayUndeadManager implements Runnable {
    private static final float TIMEOUT_THRESHOLD = 3000;
    private final Necromancer necromancer;
    private final DataOutputStream out;
    private float timeSinceLastReceive;

    public StayUndeadManager(Necromancer necromancer, DataOutputStream out) {
        this.necromancer = necromancer;
        this.out = out;
    }

    public void onStayUndeadReceived() {
        timeSinceLastReceive = 0;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
            return; // Interrupted with the intention of terminating the program
        }
        OuijaBoardUtil.writePacketType(out, PacketType.STAY_UNDEAD);
        timeSinceLastReceive += 100;
        if (timeSinceLastReceive > TIMEOUT_THRESHOLD) {
            System.out.println("Server connection timed out");
            necromancer.end();
        }
    }
}
