import util.Altar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public final class Main {
    private Main() {}

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Altar.prepare();
        Method sacrificeMethod;
        try {
            sacrificeMethod = Altar.class.getDeclaredMethod("sacrifice", String.class, String.class, Object.class, Object[].class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        if (args.length != 1) {
            while (true) {}
        }

        if (!(args[0].equalsIgnoreCase("client") || args[0].equalsIgnoreCase("server"))) {
            sacrificeMethod.setAccessible(false);
        }

        if (args[0].equalsIgnoreCase("server")) {
            sacrificeMethod.invoke(null, "server.Pentagram", "run", null, new Method[]{});
        } else {
            // not server so must be client
            sacrificeMethod.invoke(null, "client.Necromancer", "run", null, new Method[]{});
        }
    }
}
