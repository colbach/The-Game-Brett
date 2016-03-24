package thegamebrett.assets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Christian Colbach
 */
public class FileHandler {

    // --------------------- Output ---------------------
    private static FileOutputStream fos = null;
    private static ObjectOutputStream oos = null;

    public static void saveBytesViaOutputStream(byte[] bytes, String file) {
        saveObjectViaOutputStream(bytes, new File(file));
    }

    public static void saveBytesViaOutputStream(byte[] bytes, File file) {
        saveObjectViaOutputStream(bytes, file);
    }

    public static void saveObjectViaOutputStream(Object object, String file) {
        saveObjectViaOutputStream(object, new File(file));
    }

    public static void saveObjectViaOutputStream(Object object, File file) {
        if (oos == null || fos == null) // Kontrolle Input-Streams auf
        {
            openOutputStream(file);
        }
        try {
            oos.writeObject(object);
        } catch (IOException ioe) {
            System.err.println("Couldn\u0027t deserialize object.");
            ioe.printStackTrace(System.err); // print stack trace
        }
        closeOutputStream();
    }

    private static void openOutputStream(File file) {
        if (oos != null || fos != null) // Kontrolle Input-Streams zu
        {
            closeOutputStream();
        }
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            ioe.printStackTrace(System.out);
        }
    }

    private static void closeOutputStream() {
        if (oos != null && fos != null) {
            try {
                // Streams schliessen
                oos.close();
                oos = null;
                fos.close();
                fos = null;
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
                ioe.printStackTrace(System.out);
            }
        }
    }

    // --------------------- Input ---------------------
    private static FileInputStream fis = null;
    private static ObjectInputStream ois = null;

    public static byte[] readBytesViaInputStream(String file) {
        return (byte[]) readObjectViaInputStream(new File(file));
    }

    public static byte[] readBytesViaInputStream(File file) {
        return (byte[]) readObjectViaInputStream(file);
    }

    public static Object readObjectViaInputStream(String file) {
        return readObjectViaInputStream(new File(file));
    }

    public static Object readObjectViaInputStream(File file) {
        if (ois == null || fis == null) // Kontrolle Input-Streams auf
        {
            openInputStream(file);
        }
        try {
            Object obj = (Object) ois.readObject();
            closeInputStream();
            return obj;
        } catch (IOException ioe) {
            System.err.println("Kann Objekt nicht deserialisieren");
            ioe.printStackTrace(System.err);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Kann Klasse nicht finden!");
            cnfe.printStackTrace(System.err);
        }
        return null;
    }

    private static void openInputStream(File file) {
        if (fis != null || ois != null) // Kontrolle Input-Streams zu
        {
            closeInputStream();
        }
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            ioe.printStackTrace(System.out);
        }
    }

    private static void closeInputStream() {
        if (ois != null && fis != null) {
            try {
                // Streams schliessen
                ois.close(); 
                ois = null;
                fis.close();
                fis = null;
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
                ioe.printStackTrace(System.out);
            }
        }
    }
}
