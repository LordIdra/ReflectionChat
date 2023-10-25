import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public final class Main {
    private Main() {}

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        CustomClassLoader child = new CustomClassLoader("");
        Class<?> classToLoad = Class.forName("Test", true, child);
        Method method = classToLoad.getDeclaredMethod("myMethod");
        Object instance = classToLoad.getConstructor().newInstance();
        Object result = method.invoke(instance);
        System.out.println(result);
    }
}