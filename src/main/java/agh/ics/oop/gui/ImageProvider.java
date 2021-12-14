package agh.ics.oop.gui;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Image asset loader/cache
 */
public class ImageProvider {

    private static final Map<String, Image> cache = new HashMap<>();

    /**
     * Load an Image resource from cache or file.
     */
    public static Image getForAsset(String assetName) {
        Image image = cache.get(assetName);

        if (image == null) {
            try {
                image = new Image(new FileInputStream(assetName));
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("Invalid asset name");
            }
            cache.put(assetName, image);
        }

        return image;
    }
}
