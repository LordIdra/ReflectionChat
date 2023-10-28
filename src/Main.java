import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Main {

    public static void x() {
        System.out.println("oh no");
    }

    public static void main() throws InvocationTargetException, IllegalAccessException {
        Method method = Main.class.getMethods()[0];
        method.invoke(null);
    }
}
