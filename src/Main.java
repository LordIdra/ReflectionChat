import java.lang.reflect.Method;


public final class Main {
    private Main() {}

    public static void main(String[] args) {
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
            new Pentagram().mainloop();
            //sacrificeMethod.invoke(null, "Pentagram", "summonDevil", null, new Method[]{});
        } else {
            // not server so must be client
            new Necromancer().mainloop();
            //sacrificeMethod.invoke(null, "Necromancer", "usePentagram", null, new Method[]{});
        }
    }
}
