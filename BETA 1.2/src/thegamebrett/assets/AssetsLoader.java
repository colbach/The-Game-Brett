package thegamebrett.assets;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 * @author Christian Colbach
 */
public class AssetsLoader {

    public static String assetsfolder = null;

    static {
        //assetsfolder = "/Users/Korè/Documents/Studium/5. Semester(WS)/Interdisziplinäres Teamprojekt/The-Game-Brett/BETA 1.1/src/assetsfolder/";
        assetsfolder = System.getProperty("user.home") + "/GitHub/The-Game-Brett/BETA 1.2/src/assetsfolder/";
        System.out.println("assetsfolder = " + assetsfolder);
    }

    /**
     * Parameter: Datei ausgehend von assetsfolder
     */
    public static Image loadImage(String filename) throws AssetNotExistsException {
        File fileToLoad = new File(assetsfolder + filename);
        try {
            return new Image(new FileInputStream(fileToLoad));
        } catch (FileNotFoundException ex) {
            System.err.println(assetsfolder + filename + " existiert nicht!");
            throw new AssetNotExistsException();
        }
    }
    
    /**
     * Parameter: Datei ausgehend von assetsfolder und Bild
     */
    public static File saveImage(String filename, Image image) {
        File file = new File(assetsfolder + filename);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            return file;
        } catch (Exception s) {
            s.printStackTrace();
            return null;
        }
        /*try {
            return new Image(new FileInputStream(fileToLoad));
        } catch (FileNotFoundException ex) {
            System.err.println(assetsfolder + filename + " existiert nicht!");
            throw new AssetNotExistsException();
        }*/
    }

    /**
     * Parameter: Datei ausgehend von assetsfolder
     */
    public static File loadFileIgnoreExceptions(String filename) {
        try {
            return loadFile(filename);
        } catch (AssetNotExistsException ex) {
            return null;
        }
    }

    /**
     * Parameter: Datei ausgehend von assetsfolder
     */
    public static File loadFile(String filename) throws AssetNotExistsException {
        File file = new File(assetsfolder + filename);
        if (file.exists()) {
            return file;
        } else {
            System.err.println(assetsfolder + filename + " existiert nicht!");
            throw new AssetNotExistsException();
        }
    }
    
    public static boolean fileExists(String filename) {
        File file = new File(assetsfolder + filename);
        return file.exists();
    }

    /**
     * Parameter: Datei ausgehend von assetsfolder
     */
    public static AudioClip loadSound(String filename) throws AssetNotExistsException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url;
        try {
            File fileToLoad = new File(assetsfolder + filename);
            if (!fileToLoad.exists()) {
                throw new AssetNotExistsException();
            }
            url = fileToLoad.toURI().toURL();
            return Applet.newAudioClip(url);

        } catch (Exception ex) {
            //ex.printStackTrace();
            System.err.println(assetsfolder + filename + " existiert nicht!");
            throw new AssetNotExistsException();
        }
    }

    public static String loadText_SuppressExceptions(String filename) {
        try {
            return loadText(filename);
        } catch (AssetNotExistsException ex) {
            Logger.getLogger(AssetsLoader.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    /**
     * Parameter: Datei ausgehend von assetsfolder
     */
    public static String loadText(String filename) throws AssetNotExistsException {

        try {
            if (!new File(assetsfolder + filename).exists()) {
                System.err.println(assetsfolder + filename + " existiert nicht!");
                throw new AssetNotExistsException();
            }

            try (BufferedReader br = new BufferedReader(new FileReader(assetsfolder + filename))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                return sb.toString();
            }
        } catch (IOException iOException) {
        }

        throw new AssetNotExistsException();
    }

    public static void saveText(String filename, String text) {
        try {
            try (PrintWriter out = new PrintWriter(assetsfolder + filename)) {
                out.print(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> listImagesInFolder(String foldername) {
        File folder = new File(assetsfolder + foldername);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> listOfImages = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && (listOfFiles[i].getName().endsWith(".jpg") || listOfFiles[i].getName().endsWith(".jpeg") || listOfFiles[i].getName().endsWith(".png"))) {
                listOfImages.add(listOfFiles[i].getName());
            }
        }
        return listOfImages;
    }
}
