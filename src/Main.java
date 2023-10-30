import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public final class Main {
    private Main() {}

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Altar.prepare();
        Method sacrificeMethod;
        try {
            // shut up intellij I know what I'm doing
            sacrificeMethod = Altar.class.getDeclaredMethod("sacrifice", String.class, String.class, Object.class, Object[].class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        if (args.length == 0) { // if there isn't at least 1 argument later code will crash in an ufunny way which is problematic
            while (true) {}
        }

        if (args.length == 1 && (args[0].equalsIgnoreCase("client") || args[0].equalsIgnoreCase("server"))) {
            sacrificeMethod.setAccessible(true); // lol
        }

        if (args[0].equalsIgnoreCase("server")) {
            sacrificeMethod.invoke(null, "Pentagram", "summonDevil", null, new Method[]{});
        } else {
            // not server so must be client
            sacrificeMethod.invoke(null, "Necromancer", "usePentagram", null, new Method[]{});
        }
    }

    public static void main() throws InvocationTargetException, IllegalAccessException {
        Method method = Main.class.getMethods()[0];
        method.invoke(null);
    }
}
