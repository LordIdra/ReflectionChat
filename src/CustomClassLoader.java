import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class CustomClassLoader extends ClassLoader {
    private final String name;

    public CustomClassLoader(String name) {
        super();
        this.name = name;
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        byte[] b = loadFile();
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadFile()  {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name);
        System.out.println(inputStream);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {
            int nextValue ;
            while ((nextValue = inputStream.read()) != -1) {
                byteStream.write(nextValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteStream.toByteArray();
    }
}