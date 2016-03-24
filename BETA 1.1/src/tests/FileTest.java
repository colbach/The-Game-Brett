package tests;

import thegamebrett.assets.AssetNotExistsException;
import thegamebrett.assets.AssetsLoader;

/**
 * @author Christian Colbach
 */
public class FileTest {
    public static void main(String[] args) throws AssetNotExistsException {
        String test = "potato";
        AssetsLoader.saveText("test", test);
        String s = AssetsLoader.loadText("test");
        System.out.println("*"+s+"*");
    }
}
