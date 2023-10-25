import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public final class Main {
    private Main() {}

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
        URL url = new URL("https://github.com/LordIdra/ReflectionChat/raw/main/src/Test.class");
        System.out.println(url.getContent());
        URLClassLoader loader = new URLClassLoader(new URL[] { url });
        Class<?> classToLoad = loader.loadClass("Test");
        Method method = classToLoad.getDeclaredMethod("myMethod");
        Object instance = classToLoad.getConstructor().newInstance();
        Object result = method.invoke(instance);
        System.out.println(result);
    }
}