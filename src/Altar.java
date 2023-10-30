import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


public final class Altar {
    private static final Set<String> victims = Set.of("Pentagram");
    private static final Map<String, Map<String, Method>> classes = new HashMap<>();

    private Altar() {}

    // Loads all classes and their corresponding methods, ready to be sacrificed
    public static void prepare() {
        URLClassLoader loader = getUrlClassLoader();
        for (String victim : victims) {
            Class<?> loadedClass;
            try {
                loadedClass = loader.loadClass(victim);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Make map of method names to methods
            Map<String, Method> methods = Arrays.stream(loadedClass.getMethods())
                    .collect(Collectors.toMap(Method::getName, Function.identity(), (a, b) -> b));

            classes.put(loadedClass.getName(), methods);
        }
    }

    private static URLClassLoader getUrlClassLoader() {
        try {
            URL url = new URI("https://raw.githubusercontent.com/LordIdra/ReflectionChat/main/real_src/").toURL();
            return new URLClassLoader(new URL[] { url });
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Invoke method <method> from class <className> on object <victim> with arguments <arguments>
    private static Object sacrifice(String className, String method, Object victim, Object... arguments) {
        // Doesn't actually throw the IllegalAccessException because we made everything public :)
        // Can still throw InvocationTargetException if I forget to make something static lol
        try {
            return classes.get(className).get(method).invoke(victim, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
